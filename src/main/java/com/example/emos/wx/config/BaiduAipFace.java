package com.example.emos.wx.config;

import com.baidu.aip.face.AipFace;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Configuration
@Slf4j
public class BaiduAipFace{
    @Value("${baidu.APP_ID}")
    String APP_ID;

    @Value("${baidu.API_KEY}")
    String API_KEY;

    @Value("${baidu.SECRET_KEY}")
    String SECRET_KEY;

    @Bean
    public AipFace GetClient() {
        //设置APPID/AK/SK



        AipFace client = new AipFace(APP_ID, API_KEY, SECRET_KEY);

/*        // 可选：设置网络连接参数
        client.setConnectionTimeoutInMillis(2000);
        client.setSocketTimeoutInMillis(60000);

        // 可选：设置代理服务器地址, http和socket二选一，或者均不设置
        client.setHttpProxy("proxy_host", proxy_port);  // 设置http代理
        client.setSocketProxy("proxy_host", proxy_port);  // 设置socket代理*/

        return client;
    }
}
