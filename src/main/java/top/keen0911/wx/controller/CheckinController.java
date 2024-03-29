package top.keen0911.wx.controller;


import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import top.keen0911.wx.common.util.R;
import top.keen0911.wx.config.SystemConstants;
import top.keen0911.wx.config.shiro.JwtUtil;
import top.keen0911.wx.controller.form.CheckinForm;
import top.keen0911.wx.controller.form.SearchMonthCheckinForm;
import top.keen0911.wx.exception.KeenException;
import top.keen0911.wx.service.CheckinService;
import top.keen0911.wx.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

@RequestMapping("/checkin")
@RestController
@Api("签到模块Web接口")
@Slf4j
public class CheckinController {
    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private CheckinService checkinService;

    @Value("${keen.image-folder}")
    private String imageFolder;

    @Autowired
    private UserService userService;

    @Autowired
    private SystemConstants constants;

    @GetMapping("/validCanCheckIn")
    @ApiOperation("查看用户今天是否可以签到")
    public R vaildCanCheckIn(@RequestHeader("token") String token) {
        int userId = jwtUtil.getUserId(token);
        String result = checkinService.validCanCheckIn(userId, DateUtil.today());
        return R.ok(result);
    }
    @PostMapping("/checkin")
    @ApiOperation("签到")
    public R checkin(@Valid CheckinForm form, @RequestParam("photo") MultipartFile file, @RequestHeader("token") String token){
        if(file==null){
            return R.error("没有上传文件");
        }
        int userId=jwtUtil.getUserId(token);
        String fileName=file.getOriginalFilename().toLowerCase();

        String dateDir = new SimpleDateFormat("/yyyy/MM/dd").format(new Date());
        String localDirPath = imageFolder + dateDir;
        System.out.println(localDirPath);
        //创建目录
        File dirFile = new File(localDirPath);
        if (!dirFile.exists()){//不存在创建目录
            dirFile.mkdirs();
        }
        String path=localDirPath+"/"+fileName+"ID"+userId;
        try{
            file.transferTo(Paths.get(path));
            HashMap param=new HashMap();
            param.put("userId",userId);
            param.put("path",path);
            param.put("city",form.getCity());
            param.put("district",form.getDistrict());
            param.put("address",form.getAddress());
            param.put("country",form.getCountry());
            param.put("province",form.getProvince());
            checkinService.checkin(param);
            return R.ok("签到成功");
        }catch (IOException e){
            log.error(e.getMessage(),e);
            throw new KeenException("图片保存错误");
        }
    }

    @PostMapping("/createFaceModel")
    @ApiOperation("创建人脸模型")
    public R createFaceModel(@RequestParam("photo") MultipartFile file,@RequestHeader("token") String token){
        if(file==null){
            return R.error("没有上传文件");
        }
        int userId=jwtUtil.getUserId(token);
        String fileName=file.getOriginalFilename().toLowerCase();

        String path=imageFolder+"/"+fileName;
        try{
            file.transferTo(Paths.get(path));
            checkinService.createFaceModel(userId,path);
            return R.ok("人脸建模成功");
        }catch (IOException e){
            log.error(e.getMessage(),e);
            throw new KeenException("图片保存错误");
        }
        finally {
            FileUtil.del(path);
        }

    }

    @GetMapping("/searchTodayCheckin")
    @ApiOperation("查询用户当日签到数据")
    public R searchTodayCheckin(@RequestHeader("token") String token){
        int userId=jwtUtil.getUserId(token);
        HashMap map=checkinService.searchTodayCheckin(userId);
        map.put("attendanceTime",constants.attendanceTime);
        map.put("closingTime",constants.closingTime);
        long days=checkinService.searchCheckinDays(userId);
        map.put("checkinDays",days);

        DateTime hiredate=DateUtil.parse(userService.searchUserHiredate(userId));
        DateTime startDate=DateUtil.beginOfWeek(DateUtil.date());
        if(startDate.isBefore(hiredate)){
            startDate=hiredate;
        }
        DateTime endDate=DateUtil.endOfWeek(DateUtil.date());
        HashMap param=new HashMap();
        param.put("startDate",startDate.toString());
        param.put("endDate",endDate.toString());
        param.put("userId",userId);
        ArrayList<HashMap> list=checkinService.searchWeekCheckin(param);
        map.put("weekCheckin",list);
        return R.ok().put("result",map);
    }

    @PostMapping("/searchMonthCheckin")
    @ApiOperation("查询用户某月签到数据")
    public R searchMonthCheckin(@Valid @RequestBody SearchMonthCheckinForm form, @RequestHeader("token") String token){
        int userId=jwtUtil.getUserId(token);
        DateTime hiredate=DateUtil.parse(userService.searchUserHiredate(userId));
        String month=form.getMonth()<10?"0"+form.getMonth():form.getMonth().toString();
        DateTime startDate=DateUtil.parse(form.getYear()+"-"+month+"-01");
        if(startDate.isBefore(DateUtil.beginOfMonth(hiredate))){
            throw new KeenException("只能查询考勤之后日期的数据");
        }
        if(startDate.isBefore(hiredate)){
            startDate=hiredate;
        }
        DateTime endDate=DateUtil.endOfMonth(startDate);
        HashMap param=new HashMap();
        param.put("userId",userId);
        param.put("startDate",startDate.toString());
        param.put("endDate",endDate.toString());
        ArrayList<HashMap> list=checkinService.searchMonthCheckin(param);
        int sum_1=0,sum_2=0,sum_3=0;
        for(HashMap<String,String> one:list){
            String type=one.get("type");
            String status=one.get("status");
            if("工作日".equals(type)){
                if("正常".equals(status)){
                    sum_1++;
                }
                else if("迟到".equals(status)){
                    sum_2++;
                }
                else if("缺勤".equals(status)){
                    sum_3++;
                }
            }
        }
        return R.ok().put("list",list).put("sum_1",sum_1).put("sum_2",sum_2).put("sum_3",sum_3);
    }

}
