package top.keen0911.wx.service.impl;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.json.JSONArray;
import top.keen0911.wx.db.mapper.MeetingMapper;
import top.keen0911.wx.db.mapper.UserMapper;
import top.keen0911.wx.db.pojo.Meeting;
import top.keen0911.wx.exception.KeenException;
import top.keen0911.wx.service.MeetingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
@Slf4j
public class MeetingServiceImpl implements MeetingService {
    @Autowired
    private MeetingMapper meetingMapper;
    @Autowired
    private UserMapper userMapper;
/*    @Value("${keen.code}")
    private String code;
    @Value("${workflow.url}")
    private String workflow;
    @Value("${keen.recieveNotify}")
    private String recieveNotify;*/
    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public void insertMeeting(Meeting entity) {
        int row = meetingMapper.insertMeeting(entity);
        if (row != 1) {
            throw new KeenException("会议添加失败");
        }
        //开启审批工作流
/*        startMeetingWorkflow(entity.getUuid(), entity.getCreatorId().intValue(), entity.getDate(), entity.getStart());*/
    }

    @Override
    public ArrayList<HashMap> searchMyMeetingListByPage(HashMap param) {
        ArrayList<HashMap> list = meetingMapper.searchMyMeetingListByPage(param);
        String date = null;
        ArrayList resultList = new ArrayList();
        HashMap resultMap = null;
        JSONArray array = null;
        for (HashMap map : list) {
            String temp = map.get("date").toString();
            if (!temp.equals(date)) {
                date = temp;
                resultMap = new HashMap();
                resultMap.put("date", date);
                array = new JSONArray();
                resultMap.put("list", array);
                resultList.add(resultMap);
            }
            array.put(map);
        }
        return resultList;
    }

    @Override
    public HashMap searchMeetingById(int id) {
        HashMap map = meetingMapper.searchMeetingById(id);
        ArrayList<HashMap> list = meetingMapper.searchMeetingMembers(id);
        map.put("members", list);
        return map;
    }

    @Override
    public void updateMeetingInfo(HashMap param) {
/*        int id = (int) param.get("id");
        String date = param.get("date").toString();
        String start = param.get("start").toString();
        String instanceId = param.get("instanceId").toString();

        String uuid = oldMeeting.get("uuid").toString();
        Integer creatorId = Integer.parseInt(oldMeeting.get("creatorId").toString());

        HashMap oldMeeting = meetingMapper.searchMeetingById(id);*/
        int row = meetingMapper.updateMeetingInfo(param);
        if (row != 1) {
            throw new KeenException("会议更新失败");
        }
       /* JSONObject json = new JSONObject();
        json.set("instanceId", instanceId);
        json.set("reason", "会议被修改");
        json.set("uuid", uuid);
        json.set("code", code);
        String url = workflow + "/workflow/deleteProcessById";
        HttpResponse resp = HttpRequest.post(url).header("content-type", "application/json")
                .body(json.toString()).execute();
        if (resp.getStatus() != 200) {
            log.error("删除工作流失败");
            throw new KeenException("删除工作流失败");
        }
        startMeetingWorkflow(uuid, creatorId, date, start);*/
    }

    @Override
    public void deleteMeetingById(int id) {
        HashMap meeting = meetingMapper.searchMeetingById(id);
/*        String uuid = meeting.get("uuid").toString();
        String instanceId = meeting.get("instanceId").toString();*/
        DateTime date = DateUtil.parse(meeting.get("date") + " " + meeting.get("start"));
        DateTime now = DateUtil.date();
        if (now.isAfterOrEquals(date.offset(DateField.MINUTE, -5))) {
            throw new KeenException("距离会议开始不足5分钟，不能删除会议");
        }
        int row = meetingMapper.deleteMeetingById(id);
        if (row != 1) {
            throw new KeenException("会议删除失败");
        }
        /*JSONObject json = new JSONObject();
        json.set("instanceId", instanceId);
        json.set("reason", "会议被修改");
        json.set("uuid", uuid);
        json.set("code", code);
        String url = workflow + "/workflow/deleteProcessById";
        HttpResponse resp = HttpRequest.post(url).header("content-type", "application/json")
                .body(json.toString()).execute();
        if (resp.getStatus() != 200) {
            log.error("删除工作流失败");
            throw new KeenException("删除工作流失败");
        }*/
    }

    @Override
    public Long searchRoomIdByUUID(String uuid) {
        Object temp=redisTemplate.opsForValue().get(uuid);
        long roomId=Long.parseLong(temp.toString());
        return roomId;
    }

    @Override
    public List<String> searchUserMeetingInMonth(HashMap param) {
        List<String> list=meetingMapper.searchUserMeetingInMonth(param);
        return list;
    }

/*    private void startMeetingWorkflow(String uuid, int creatorId, String date, String start) {
        HashMap info = userMapper.searchUserInfo(creatorId);
        JSONObject json = new JSONObject();
        json.set("url", recieveNotify);
        json.set("uuid", uuid);
        json.set("openId", info.get("openId"));
        json.set("code", code);
        json.set("date", date);
        json.set("start", start);
        String[] roles = info.get("roles").toString().split("，");
        if (!ArrayUtil.contains(roles, "总经理")) {
            Integer managerId = userMapper.searchDeptManagerId(creatorId);
            json.set("managerId", managerId);
            Integer gmId = userMapper.searchGmId();
            json.set("gmId", gmId);
            boolean bool = meetingMapper.searchMeetingMembersInSameDept(uuid);
            json.set("sameDept", bool);
        }
        String url = workflow + "/workflow/startMeetingProcess";
        HttpResponse resp = HttpRequest.post(url).header("Content-Type", "application/json")
                .body(json.toString()).execute();
        if (resp.getStatus() == 200) {
            json = JSONUtil.parseObj(resp.body());
            String instanceId = json.getStr("instanceId");
            HashMap param = new HashMap();
            param.put("uuid", uuid);
            param.put("instanceId", instanceId);
            int row = meetingMapper.updateMeetingInstanceId(param);
            if (row != 1) {
                throw new KeenException("保存会议工作流实例ID失败");
            }
        }
    }*/
}
