package top.keen0911.wx.db.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import top.keen0911.wx.db.pojo.Dept;

import java.util.ArrayList;
import java.util.HashMap;

/**
* @author keen
* @description 针对表【tb_dept】的数据库操作Mapper
* @createDate 2022-09-20 09:39:58
* @Entity Dept
*/
public interface DeptMapper extends BaseMapper<Dept> {

    public ArrayList<HashMap> searchDeptMembers(String keyword);
    public Integer searchDeptByRecode(String recode);

    public int insert(HashMap param);
}




