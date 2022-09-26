package top.keen0911.wx.api;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import top.keen0911.wx.db.mapper.UserMapper;
import top.keen0911.wx.db.pojo.User;

@SpringBootTest
class keentestmybatisplus {
    @Autowired

    private UserMapper userMapper;

    @Test
    public void queryTest(){
        User user = userMapper.selectById(34);
        QueryWrapper<User> queryWrapper = new QueryWrapper<User>();
        queryWrapper.eq(true,"id",40);
        userMapper.exists(queryWrapper);

        User user1 = new User();
        user1.setId(34);
        user1.setUsername("nihaoa");
        user1.setNickname("keen");
        userMapper.updateById(user1);
        System.out.println(userMapper.exists(queryWrapper));
        System.out.println(userMapper.updateById(user1));
        System.out.println(user);

    }

}
