package top.keen0911.wx.db.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import top.keen0911.wx.db.pojo.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

/**
* @author keen
* @description 针对表【tb_user(用户表)】的数据库操作Mapper
* @createDate 2022-09-20 09:39:59
* @Entity User
*/
public interface UserMapper extends BaseMapper<User> {

    boolean haveRootUser();

    int insert(HashMap param);

    Integer searchIdByOpenId(String openId);

    Set<String> searchUserPermissions(int userId);

    User searchById(int userId);

    HashMap searchNameAndDept(int userId);

    String searchUserHiredate(int userId);

    HashMap searchUserSummary(int userId);

    public ArrayList<HashMap> searchUserGroupByDept(String keyword);

    public ArrayList<HashMap> searchMembers(List param);

    public HashMap searchUserInfo(int userId);

    public int searchDeptManagerId(int id);

    public int searchGmId();

    public List<HashMap> selectUserPhotoAndName(List param);

    public String searchMemberEmail(int id);

    public int updateWebAcc(HashMap param);
}




