package top.keen0911.wx.db.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import top.keen0911.wx.db.pojo.Checkin;

import java.util.ArrayList;
import java.util.HashMap;

/**
* @author keen
* @description 针对表【tb_checkin(签到表)】的数据库操作Mapper
* @createDate 2022-09-20 09:39:58
* @Entity Checkin
*/
public interface CheckinMapper extends BaseMapper<Checkin> {
    Integer haveCheckin(HashMap map);
    int insert(Checkin checkin);
    HashMap searchTodayCheckin(int userId);
    long searchCheckinDays(int userId);
    ArrayList<HashMap> searchWeekCheckin(HashMap param);
}




