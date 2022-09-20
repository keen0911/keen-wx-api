package top.keen0911.wx.db.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import top.keen0911.wx.db.pojo.City;

/**
* @author keen
* @description 针对表【tb_city(疫情城市列表)】的数据库操作Mapper
* @createDate 2022-09-20 09:39:58
* @Entity City
*/
public interface CityMapper extends BaseMapper<City> {
    String searchCode(String city);
}




