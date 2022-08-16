package com.example.keen.wx.db.dao;

import com.example.keen.wx.db.pojo.TbUser;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

@Mapper
public interface TbUserDao {
    boolean haveRootUser();

    int insert(HashMap param);

    Integer searchIdByOpenId(String openId);

    Set<String> searchUserPermissions(int userId);

    TbUser searchById(int userId);

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
