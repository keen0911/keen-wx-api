package top.keen0911.wx.db.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import top.keen0911.wx.db.pojo.Workday;

import java.util.ArrayList;
import java.util.HashMap;

/**
* @author keen
* @description 针对表【tb_workday】的数据库操作Mapper
* @createDate 2022-09-20 09:39:59
* @Entity Workday
*/
public interface WorkdayMapper extends BaseMapper<Workday> {
    Integer searchTodayIsWorkday();
    ArrayList<String> searchWorkdayInRange(HashMap param);
}




