package top.keen0911.wx.controller;


import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.json.JSONUtil;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.web.multipart.MultipartFile;
import top.keen0911.wx.common.util.R;
import top.keen0911.wx.config.shiro.JwtUtil;
import top.keen0911.wx.config.tencent.TLSSigAPIv2;
import top.keen0911.wx.controller.form.*;
import top.keen0911.wx.db.pojo.MessageEntity;
import top.keen0911.wx.db.pojo.User;
import top.keen0911.wx.exception.KeenException;
import top.keen0911.wx.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/user")
@Api("用户模块Web接口")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private RedisTemplate redisTemplate;

    @Value("${keen.jwt.cache-expire}")
    private int cacheExpire;
    @Value("${trtc.appid}")
    private Integer appid;

    @Value("${trtc.key}")
    private String key;

    @Value("${trtc.expire}")
    private Integer expire;

    private void saveCacheToken(String token, int userId) {
        redisTemplate.opsForValue().set(token, userId + "", cacheExpire, TimeUnit.DAYS);
    }

    @PostMapping("/register")
    @ApiOperation("注册用户")
    public R register(@Valid @RequestBody RegisterForm form) {
        int id = userService.registerUser(form.getRegisterCode(), form.getCode(), form.getNickname(), form.getPhoto());
        userService.setWebLoginId(id,form.getTid());
        String token = jwtUtil.createToken(id);
        Set<String> permsSet = userService.searchUserPermissions(id);
        saveCacheToken(token, id);
        return R.ok("用户注册成功").put("token", token).put("permission", permsSet);
    }

    @PostMapping("/login")
    @ApiOperation("登录系统")
    public R login(@Valid @RequestBody LoginForm form) {
        int id = userService.login(form.getCode());
        userService.setWebLoginId(id,form.getTid());
        String token = jwtUtil.createToken(id);
        Set<String> permsSet = userService.searchUserPermissions(id);
        saveCacheToken(token, id);
        return R.ok("登录成功").put("token", token).put("permission", permsSet);
    }

    @GetMapping("/searchUserSummary")
    @ApiOperation("查询用户摘要信息")
    @RequiresPermissions(value = {"ROOT","USER:SELECT"},logical = Logical.OR)
    public R searchUserSummary(@RequestHeader("token") String token){
        int userId=jwtUtil.getUserId(token);
        HashMap map=userService.searchUserSummary(userId);
        return R.ok().put("result",map);
    }
    @GetMapping("/selectUserInfo")
    @ApiOperation("查询用户信息")
    public R selectUserInfo(@RequestHeader("token") String token){
        int userId=jwtUtil.getUserId(token);
        User rspUser=userService.getById(userId);
        System.out.println(rspUser+"-----------------------------------------");
        return R.ok().put("result",rspUser);
    }
    @PostMapping("/searchUserGroupByDept")
    @ApiOperation("查询员工列表，按照部门分组排列")
    @RequiresPermissions(value = {"ROOT","EMPLOYEE:SELECT"},logical = Logical.OR)
    public R searchUserGroupByDept(@Valid @RequestBody SearchUserGroupByDeptForm form){
        ArrayList<HashMap> list=userService.searchUserGroupByDept(form.getKeyword());
        return R.ok().put("result",list);
    }

    @PostMapping("/searchMembers")
    @ApiOperation("查询成员")
    @RequiresPermissions(value = {"ROOT", "MEETING:INSERT", "MEETING:UPDATE"},logical = Logical.OR)
    public R searchMembers(@Valid @RequestBody SearchMembersForm form){
        if(!JSONUtil.isJsonArray(form.getMembers())){
            throw new KeenException("members不是JSON数组");
        }
        List param=JSONUtil.parseArray(form.getMembers()).toList(Integer.class);
        ArrayList list=userService.searchMembers(param);
        return R.ok().put("result",list);
    }

    @PostMapping("/selectUserPhotoAndName")
    @ApiOperation("查询用户姓名和头像")
    @RequiresPermissions(value = {"WORKFLOW:APPROVAL"})
    public R selectUserPhotoAndName(@Valid @RequestBody SelectUserPhotoAndNameForm form){
        if(!JSONUtil.isJsonArray(form.getIds())){
            throw new KeenException("参数不是JSON数组");
        }
        List<Integer> param=JSONUtil.parseArray(form.getIds()).toList(Integer.class);
        List<HashMap> list=userService.selectUserPhotoAndName(param);
        return R.ok().put("result",list);
    }

    @PostMapping("/userUpd")
    @ApiOperation("更新用户信息")
    public R checkin(@Valid @RequestBody User form, @RequestHeader("token") String token){
        int userId=jwtUtil.getUserId(token);
        form.setId(userId);
        if(!(form.getEmail()==null)){
            userService.sendAccToEmail(form);
        }
        form.setCreateTime(new Date());
        return userService.updateById(form)?R.ok("保存成功"):R.error("修改失败");
    }
    @GetMapping("/genUserSig")
    @ApiOperation("生成用户签名")
    public R genUserSig(@RequestHeader("token") String token){
        int id=jwtUtil.getUserId(token);
        String email=userService.searchMemberEmail(id);
        TLSSigAPIv2 api=new TLSSigAPIv2(appid,key);
        String userSig=api.genUserSig(email,expire);
        return R.ok().put("userSig",userSig).put("email",email);
    }

}
