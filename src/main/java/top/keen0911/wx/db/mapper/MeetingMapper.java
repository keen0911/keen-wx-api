package top.keen0911.wx.db.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import top.keen0911.wx.db.pojo.Meeting;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
* @author keen
* @description 针对表【tb_meeting(会议表)】的数据库操作Mapper
* @createDate 2022-09-20 09:39:58
* @Entity Meeting
*/
public interface MeetingMapper extends BaseMapper<Meeting> {

    public int insertMeeting(Meeting entity);

    public ArrayList<HashMap> searchMyMeetingListByPage(HashMap param);

    public boolean searchMeetingMembersInSameDept(String uuid);

    public int updateMeetingInstanceId(HashMap map);

    public HashMap searchMeetingById(int id);

    public ArrayList<HashMap> searchMeetingMembers(int id);

    public int updateMeetingInfo(HashMap param);

    public int deleteMeetingById(int id);

    public List<String> searchUserMeetingInMonth(HashMap param);
}




