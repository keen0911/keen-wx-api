package com.example.keen.wx;

import cn.hutool.core.util.StrUtil;
import com.example.keen.wx.config.SystemConstants;
import com.example.keen.wx.db.dao.SysConfigDao;
import com.example.keen.wx.db.pojo.SysConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;

import javax.annotation.PostConstruct;
import java.lang.reflect.Field;
import java.util.List;

@SpringBootApplication
@ServletComponentScan
@Slf4j
@EnableAsync
public class KeenWxApiApplication {
    @Autowired
    private SysConfigDao sysConfigDao;

    @Autowired
    private SystemConstants constants;

    public static void main(String[] args) {
        SpringApplication.run(KeenWxApiApplication.class, args);
    }

    @PostConstruct
    public void init() {
        List<SysConfig> sysConfigs = sysConfigDao.selectAllParam();
        sysConfigs.forEach(one->{
            String paramKey = one.getParamKey();
            paramKey= StrUtil.toCamelCase(paramKey);
            String value = one.getParamValue();
            try {
                Field field = constants.getClass().getDeclaredField(paramKey);
                field.set(constants,value);
            } catch (Exception e) {
                log.error("执行异常",e);
            }

        });


    }
}
