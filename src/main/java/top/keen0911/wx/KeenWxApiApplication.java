package top.keen0911.wx;

import cn.hutool.core.util.StrUtil;
import top.keen0911.wx.config.SystemConstants;
import top.keen0911.wx.db.mapper.CheckinConfigMapper;
import top.keen0911.wx.db.pojo.CheckinConfig;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
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
@MapperScan("top.keen0911.wx.db.mapper")
@Slf4j
@EnableAsync
public class KeenWxApiApplication {
    @Autowired
    private CheckinConfigMapper CheckinConfigMapper;

    @Autowired
    private SystemConstants constants;

    public static void main(String[] args) {
        SpringApplication.run(KeenWxApiApplication.class, args);
    }

    @PostConstruct
    public void init() {

        List<CheckinConfig> tbCheckinConfigs = CheckinConfigMapper.selectAllParam();
        tbCheckinConfigs.forEach(one->{
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
