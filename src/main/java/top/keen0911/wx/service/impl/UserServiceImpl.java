package top.keen0911.wx.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.mail.MailSendException;
import org.springframework.mail.SimpleMailMessage;
import top.keen0911.wx.db.mapper.DeptMapper;
import top.keen0911.wx.db.mapper.UserMapper;
import top.keen0911.wx.db.pojo.Dept;
import top.keen0911.wx.db.pojo.MessageEntity;
import top.keen0911.wx.db.pojo.User;
import top.keen0911.wx.exception.KeenException;
import top.keen0911.wx.service.UserService;

import top.keen0911.wx.task.EmailTask;
import top.keen0911.wx.task.MessageTask;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
@Slf4j
@Scope("prototype")
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Value("${wx.app-id}")
    private String appId;

    @Value("${wx.app-secret}")
    private String appSecret;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private MessageTask messageTask;

    @Autowired
    private DeptMapper deptMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private EmailTask emailTask;

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

        User userBean = new User();
        userBean.setOpenId(openId);
        userBean.setNickname(nickname);
        userBean.setPhoto(photo);
        userBean.setName("keener");
        userBean.setHiredate(new Date());
        userBean.setCreateTime(DateUtil.date());
        userBean.setStatus((byte) 1);
        userBean.setRoot(false);
        userBean.setRole("[3]");



        Integer deptId=deptMapper.searchDeptByRecode(registerCode);
        if (userMapper.searchIdByOpenId(openId)!=null){
            throw new KeenException("已存在账号");
        }
        if(registerCode.equals("000000")){
            boolean bool=userMapper.haveRootUser();
            if(!bool){
                userBean.setRoot(true);
                userBean.setRole("[0]");
                userBean.setUsername("keen");
                userMapper.insert(userBean);
                id=userMapper.searchIdByOpenId(openId);

                HashMap acc=new HashMap();
                acc.put("userId",id);
                acc.put("username","keen");
                acc.put("password","keen0911");
                userMapper.updateWebAcc(acc);

                MessageEntity entity=new MessageEntity();
                entity.setSenderId(0);
                entity.setSenderName("系统消息");
                entity.setUuid(IdUtil.simpleUUID());
                entity.setMsg("你好keen");
                entity.setSendTime(new Date());
                messageTask.sendAsync(id+"",entity);
            }
            else{
                throw new KeenException("无法绑定超级管理员账号");
            }
        }
        else if(registerCode.equals("班长")){
            userBean.setRole("[1]");
            userMapper.insert(userBean);
            id=userMapper.searchIdByOpenId(openId);

            Dept dept = new Dept();
            dept.setId(id);
            dept.setDept_name("keen"+id+"班");
            dept.setStatus(1);
            deptMapper.insert(dept);

            User user = new User();
            user.setId(id);
            user.setDeptId(id);
            userMapper.updateById(user);

            MessageEntity entity=new MessageEntity();
            entity.setSenderId(0);
            entity.setSenderName("系统消息");
            entity.setUuid(IdUtil.simpleUUID());
            entity.setMsg("已为你初始化班级，请及时在我的-班级管理更新你的班级信息。及时更新你的邮箱信息以便系统发送你的的web账号和初始密码");
            entity.setSendTime(new Date());
            messageTask.sendAsync(id+"",entity);
        }
        else if(deptId!=null){
                userBean.setDeptId(deptId);
                userMapper.insert(userBean);
                id=userMapper.searchIdByOpenId(openId);

                MessageEntity entity=new MessageEntity();
                entity.setSenderId(0);
                entity.setSenderName("系统消息");
                entity.setUuid(IdUtil.simpleUUID());
                entity.setMsg("欢迎您注册成为一名keener，这是你的web端账号：keen"+id+"，初始密码为keen0911，请及时到www.keen0911.top修改你的web密码。");
                entity.setSendTime(new Date());
                messageTask.sendAsync(id+"",entity);
        }
        else {
            throw new KeenException("输入正确的邀请码或邀请码已失效");
        }
        return id;
    }

    @Override
    public Set<String> searchUserPermissions(int userId) {
        return userMapper.searchUserPermissions(userId);
    }

    @Override
    public Integer login(String code) {
        String openId = getOpenId(code);
        Integer id = userMapper.searchIdByOpenId(openId);
        if (id == null) {
            throw new KeenException("账户不存在");
        }
        //TODO 从消息列表接收消息，转移消息表
        return id;
    }

    @Override
    public User searchById(int userId) {
        return userMapper.searchById(userId);
    }

    @Override
    public String searchUserHiredate(int userId) {
        return userMapper.searchUserHiredate(userId);
    }

    @Override
    public HashMap searchUserSummary(int userId) {
        return userMapper.searchUserSummary(userId);
    }

    @Override
    public ArrayList<HashMap> searchUserGroupByDept(String keyword) {
        ArrayList<HashMap> list_1=deptMapper.searchDeptMembers(keyword);
        ArrayList<HashMap> list_2=userMapper.searchUserGroupByDept(keyword);
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
        ArrayList<HashMap> list=userMapper.searchMembers(param);
        return list;
    }

    @Override
    public List<HashMap> selectUserPhotoAndName(List param) {
        List<HashMap> list=userMapper.selectUserPhotoAndName(param);
        return list;
    }

    @Override
    public String searchMemberEmail(int id) {
        String email=userMapper.searchMemberEmail(id);
        return email;
    }

    @Override
    public void setWebLoginId(int id, String tid) {
        if(tid!=null){
            boolean bool = redisTemplate.hasKey(tid);
            if (bool) {
                redisTemplate.opsForValue().set(tid, id);
            }
        }
    }

    @Override
    public void sendAccToEmail(User form) {
        //发送告警邮件
        User user=userMapper.selectById(form.getId());
        if(user.getUsername().isEmpty()){
            HashMap <String,Object>map=new HashMap<>();
            map.put("userId",form.getId());
            map.put("username","keen"+form.getId());
            map.put("password",DateUtil.format(new Date(), "yyyyMMddHH"));
            userMapper.updateWebAcc(map);
        }
        HashMap map = userMapper.searchWebAcc(form.getId());
        System.out.println("你的keen智慧校园账号"+map.get("username")+"密码为" + map.get("password"));

        try {
            SimpleMailMessage message=new SimpleMailMessage();
            message.setTo(form.getEmail());
            message.setSubject("keen智慧校园找回账号密码");
            message.setText("你的keen智慧校园账号"+map.get("username")+"密码为" + map.get("password"));
            emailTask.send(message);
        }catch (MailSendException e) {
            throw new KeenException("邮件发送失败，请输入正确邮箱地址");
        }


        MessageEntity entity=new MessageEntity();
        entity.setSenderId(0);
        entity.setSenderName("系统消息");
        entity.setUuid(IdUtil.simpleUUID());
        entity.setMsg("账号信息已发送至"+form.getEmail()+"，不要再忘了");
        entity.setSendTime(new Date());
        messageTask.sendAsync(form.getId()+"",entity);
    }
}
