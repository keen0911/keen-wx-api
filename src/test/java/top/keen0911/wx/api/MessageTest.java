package top.keen0911.wx.api;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import top.keen0911.wx.db.pojo.Meeting;
import top.keen0911.wx.db.pojo.MessageEntity;
import top.keen0911.wx.db.pojo.MessageRefEntity;
import top.keen0911.wx.service.MeetingService;
import top.keen0911.wx.service.MessageService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

@SpringBootTest
public class MessageTest {
    @Autowired
    private MessageService messageService;
    @Autowired
    private MeetingService meetingService;

    @Test
    void contextLoads() {
        for (int i = 1; i <= 100; i++) {
            MessageEntity message = new MessageEntity();
            message.setUuid(IdUtil.simpleUUID());
            message.setSenderId(0);
            message.setSenderName("系统消息");
            message.setMsg("这是第" + i + "条测试消息");
            message.setSendTime(new Date());
            String id=messageService.insertMessage(message);

            MessageRefEntity ref=new MessageRefEntity();
            ref.setMessageId(id);
            ref.setReceiverId(29); //接收人ID
            ref.setLastFlag(true);
            ref.setReadFlag(false);
            messageService.insertRef(ref);
        }
    }

    @Test
    void createMeetingData(){
        for (int i=1;i<=100;i++){
            Meeting meeting=new Meeting();
            meeting.setId((long)i);
            meeting.setUuid(IdUtil.simpleUUID());
            meeting.setTitle("测试会议"+i);
            meeting.setCreator_id(15L); //ROOT用户ID
            meeting.setDate(DateUtil.today());
            meeting.setPlace("线上会议室");
            meeting.setStart("23:30");
            meeting.setEnd("23:59");
            meeting.setType((int) 1);
            meeting.setMembers("[15,16]");
            meeting.setDesc("会议研讨Emos项目上线测试");
            meeting.setInstance_id(IdUtil.simpleUUID());
            meeting.setStatus((int) 1);
            meetingService.insertMeeting(meeting);
        }
    }
}
