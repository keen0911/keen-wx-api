package top.keen0911.wx.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import top.keen0911.wx.db.pojo.User;

@Component
@Slf4j
public class DefaultBeans {

    @Bean
    public User getUser() {
        User bean = new User();
        bean.setId(1);
        bean.setDeptId(1);
        return bean;
    }
}
