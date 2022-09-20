package top.keen0911.wx.db.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import top.keen0911.wx.db.pojo.CheckinConfig;

import java.util.List;

/**
* @author keen
* @description 针对表【tb_checkin_config】的数据库操作Mapper
* @createDate 2022-09-20 10:51:10
* @Entity CheckinConfig
*/
public interface CheckinConfigMapper extends BaseMapper<CheckinConfig> {
    public List<CheckinConfig> selectAllParam();

}




