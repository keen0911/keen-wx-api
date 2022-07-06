package com.example.emos.wx.db.dao;

import com.example.emos.wx.db.pojo.TbUser;
import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;
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
}
