package com.example.keen.wx.service.impl;

import cn.hutool.core.util.IdUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.example.keen.wx.db.dao.TbDeptDao;
import com.example.keen.wx.db.dao.TbUserDao;
import com.example.keen.wx.db.pojo.MessageEntity;
import com.example.keen.wx.db.pojo.TbUser;
import com.example.keen.wx.exception.KeenException;
import com.example.keen.wx.service.UserService;

import com.example.keen.wx.task.MessageTask;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
@Slf4j
@Scope("prototype")
public class UserServiceImpl implements UserService {

    @Value("${wx.app-id}")
    private String appId;

    @Value("${wx.app-secret}")
    private String appSecret;

    @Autowired
    private TbUserDao userDao;

    @Autowired
    private MessageTask messageTask;

    @Autowired
    private TbDeptDao deptDao;


    private String getOpenId(String code){
        String url="https://api.weixin.qq.com/sns/jscode2session";
        HashMap map=new HashMap();
        map.put("appid", appId);
        map.put("secret", appSecret);
        map.put("js_code", code);
        map.put("grant_type", "authorization_code");
        String response= HttpUtil.post(url,map);
        JSONObject json= JSONUtil.parseObj(response);
        String openId=json.getStr("openid");
        if(openId==null||openId.length()==0){
            throw new RuntimeException("临时登陆凭证错误");
        }
        return openId;
    }

    @Override
    public int registerUser(String registerCode, String code, String nickname, String photo) {
        String openId=getOpenId(code);
        int id=0;
        if (userDao.searchIdByOpenId(openId)!=null){
            throw new KeenException("已存在账号");
        }
        if(registerCode.equals("000000")){
            boolean bool=userDao.haveRootUser();
            if(!bool){
                HashMap param=new HashMap();
                param.put("username","keen0911");
                param.put("password", "k254441461");
                param.put("openId", openId);
                param.put("nickname", nickname);
                param.put("photo", photo);
                param.put("name","keen");
                param.put("hiredate", new Date());
                param.put("role", "[0]");
                param.put("status", 1);
                param.put("createTime", new Date());
                param.put("root", true);
                param.put("deptId", 6);
                userDao.insert(param);
                id=userDao.searchIdByOpenId(openId);

                MessageEntity entity=new MessageEntity();
                entity.setSenderId(0);
                entity.setSenderName("系统消息");
                entity.setUuid(IdUtil.simpleUUID());
                entity.setMsg("欢迎您注册成为超级管理员，请及时更新你的员工个人信息。");
                entity.setSendTime(new Date());
                messageTask.sendAsync(id+"",entity);
            }
            else{
                throw new KeenException("无法绑定超级管理员账号");
            }
        }
        else if(registerCode.equals("梁一鸣")){
                HashMap param=new HashMap();
                param.put("openId", openId);
                param.put("username","keen6666");
                param.put("password", "k254441461");
                param.put("nickname", nickname);
                param.put("photo", photo);
                param.put("name","未设置");
                param.put("hiredate", new Date());
                param.put("role", "[3]");
                param.put("status", 1);
                param.put("createTime", new Date());
                param.put("root", false);
                param.put("deptId", 1);
                userDao.insert(param);
                id=userDao.searchIdByOpenId(openId);

                MessageEntity entity=new MessageEntity();
                entity.setSenderId(0);
                entity.setSenderName("系统消息");
                entity.setUuid(IdUtil.simpleUUID());
                entity.setMsg("欢迎您注册成为一名keener，请及时更新你的学生个人信息。");
                entity.setSendTime(new Date());
                messageTask.sendAsync(id+"",entity);
        }
        return id;
    }

    @Override
    public Set<String> searchUserPermissions(int userId) {
        return userDao.searchUserPermissions(userId);
    }

    @Override
    public Integer login(String code) {
        String openId = getOpenId(code);
        Integer id = userDao.searchIdByOpenId(openId);
        if (id == null) {
            throw new KeenException("账户不存在");
        }
        //TODO 从消息列表接收消息，转移消息表
        return id;
    }

    @Override
    public TbUser searchById(int userId) {
        return userDao.searchById(userId);
    }

    @Override
    public String searchUserHiredate(int userId) {
        return userDao.searchUserHiredate(userId);
    }

    @Override
    public HashMap searchUserSummary(int userId) {
        return userDao.searchUserSummary(userId);
    }

    @Override
    public ArrayList<HashMap> searchUserGroupByDept(String keyword) {
        ArrayList<HashMap> list_1=deptDao.searchDeptMembers(keyword);
        ArrayList<HashMap> list_2=userDao.searchUserGroupByDept(keyword);
        for(HashMap map_1:list_1){
            long deptId=(Long)map_1.get("id");
            ArrayList members=new ArrayList();
            for(HashMap map_2:list_2){
                long id=(Long) map_2.get("deptId");
                if(deptId==id){
                    members.add(map_2);
                }
            }
            map_1.put("members",members);
        }
        return list_1;
    }

    @Override
    public ArrayList<HashMap> searchMembers(List param) {
        ArrayList<HashMap> list=userDao.searchMembers(param);
        return list;
    }

    @Override
    public List<HashMap> selectUserPhotoAndName(List param) {
        List<HashMap> list=userDao.selectUserPhotoAndName(param);
        return list;
    }

    @Override
    public String searchMemberEmail(int id) {
        String email=userDao.searchMemberEmail(id);
        return email;
    }
}
