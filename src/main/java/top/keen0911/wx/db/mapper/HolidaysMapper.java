package top.keen0911.wx.db.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import top.keen0911.wx.db.pojo.Holidays;

import java.util.ArrayList;
import java.util.HashMap;

/**
* @author keen
* @description 针对表【tb_holidays(节假日表)】的数据库操作Mapper
* @createDate 2022-09-20 09:39:58
* @Entity Holidays
*/
public interface HolidaysMapper extends BaseMapper<Holidays> {

    Integer searchTodayIsHolidays();
    ArrayList<String> searchHolidaysInRange(HashMap param);
}




