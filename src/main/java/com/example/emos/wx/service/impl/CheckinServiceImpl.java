package com.example.emos.wx.service.impl;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.baidu.aip.face.AipFace;
import com.example.emos.wx.config.BaiduAipFace;
import com.example.emos.wx.config.SystemConstants;
import com.example.emos.wx.db.dao.*;
import com.example.emos.wx.db.pojo.TbCheckin;
import com.example.emos.wx.exception.EmosException;
import com.example.emos.wx.service.CheckinService;
import com.example.emos.wx.task.EmailTask;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;

@Service
@Scope("prototype")
@Slf4j
public class CheckinServiceImpl implements CheckinService {
    @Autowired
    private SystemConstants constants;

    @Autowired
    private TbHolidaysDao holidaysDao;

    @Autowired
    private TbWorkdayDao workdayDao;

    @Autowired
    private TbCheckinDao checkinDao;

    @Autowired
    private BaiduAipFace aipface;

    @Autowired
    private TbCityDao cityDao;

    @Autowired
    private TbUserDao userDao;

    @Autowired
    private EmailTask emailTask;

    @Value("${emos.email.hr}")
    private String hrEmail;



    @Override
    public String validCanCheckIn(int userId, String date) {
        boolean bool_1 = holidaysDao.searchTodayIsHolidays() != null;
        boolean bool_2 = workdayDao.searchTodayIsWorkday() != null;
        String type = "工作日";
        if (DateUtil.date().isWeekend()) {
            type = "节假日";
        }
        if (bool_1) {
            type = "节假日";
        } else if (bool_2) {
            type = "工作日";
        }
        if (type.equals("节假日")) {
            return "节假日不需要考勤";
        } else {
            DateTime now = DateUtil.date();
            String start = DateUtil.today() + " " + constants.attendanceStartTime;
            String end = DateUtil.today() + " " + constants.attendanceEndTime;
            DateTime attendanceStart = DateUtil.parse(start);
            DateTime attendanceEnd = DateUtil.parse(end);
            if (now.isBefore(attendanceStart)) {
                return "没到上班考勤开始时间";
            } else if (now.isAfter(attendanceEnd)) {
                return "超过了上班考勤时间";
            } else {
                HashMap map = new HashMap();
                map.put("userId", userId);
                map.put("date", date);
                map.put("start", start);
                map.put("end", end);
                boolean bool = checkinDao.haveCheckin(map) != null;
                return bool ? "今日已经考勤，不用重复考勤" : "可以考勤";
            }
        }
    }

    @Override
    public void checkin(HashMap param) {
        Date d1=DateUtil.date();
        Date d2=DateUtil.parse(DateUtil.today()+" "+constants.attendanceTime);
        Date d3=DateUtil.parse(DateUtil.today()+" "+constants.attendanceEndTime);
        int status=1;
        if(d1.compareTo(d2)<=0){
            status=1;
        }
        else if(d1.compareTo(d2)>0&&d1.compareTo(d3)<0){
            status=2;
        }
        else{
            throw new EmosException("超出考勤时间段，无法考勤");
        }

        AipFace client = aipface.AipFaceClient();
        int userId= (Integer) param.get("userId");




        String image=(String)param.get("path");
        String imageType = "URL";//用base64提交图片



        String groupIdList = "emos_face";
        Double score;
        HashMap<String, Object> options = new HashMap();
        options.put("match_threshold", "70");
        options.put("quality_control", "NORMAL");
        options.put("liveness_control", "LOW");
        options.put("user_id", userId);
        options.put("max_user_num", "3");




        // 人脸搜索
        JSONObject res = client.search(image, imageType, groupIdList, options);


        try {
            System.out.println(res.toString(2));
            JSONObject r = res.getJSONObject("result");
            JSONArray user_list = r.getJSONArray("user_list");
            JSONObject us = user_list.getJSONObject(0);
            score = Double.valueOf(us.getString("score"));
            System.out.println(score);

        } catch (JSONException e) {
            throw new EmosException("人脸认证失败");
        }



        if(score<80){
            throw new EmosException("签到无效，非本人签到");
        }
        else{
            int risk=1;
            String city= (String) param.get("city");
            String district= (String) param.get("district");
            String address= (String) param.get("address");
            String country= (String) param.get("country");
            String province= (String) param.get("province");
            if(!StrUtil.isBlank(city)&&!StrUtil.isBlank(district)){
                String code= cityDao.searchCode(city);
                try{
                    String url = "http://m." + code + ".bendibao.com/news/yqdengji/?qu=" + district;
                    Document document= Jsoup.connect(url).get();
                    Elements elements=document.getElementsByClass("list-content");
                    if(elements.size()>0){
                        Element element=elements.get(0);
                        String result=element.select("p:last-child").text();
//                            result="高风险";
                        if("高风险".equals(result)){
                            risk=3;
                            //发送告警邮件
                            HashMap<String,String> map=userDao.searchNameAndDept(userId);
                            String name = map.get("name");
                            String deptName = map.get("dept_name");
                            deptName = deptName != null ? deptName : "";
                            SimpleMailMessage message=new SimpleMailMessage();
                            message.setTo(hrEmail);
                            message.setSubject("员工" + name + "身处高风险疫情地区警告");
                            message.setText(deptName + "员工" + name + "，" + DateUtil.format(new Date(), "yyyy年MM月dd日") + "处于" + address + "，属于新冠疫情高风险地区，请及时与该员工联系，核实情况！");
                            emailTask.sendAsync(message);
                        }
                        else if("中风险".equals(result)){
                            risk=2;
                        }
                    }
                }catch (Exception e){
                    log.error("执行异常",e);
                    throw new EmosException("获取风险等级失败");
                }
            }
            //保存签到记录
            TbCheckin entity=new TbCheckin();
            entity.setUserId(userId);
            entity.setAddress(address);
            entity.setCountry(country);
            entity.setProvince(province);
            entity.setCity(city);
            entity.setDistrict(district);
            entity.setStatus((byte) status);
            entity.setRisk(risk);
            entity.setDate(DateUtil.today());
            entity.setCreateTime(d1);
            checkinDao.insert(entity);
        }
    }

}
