package com.example.keen.wx.config;

import com.rabbitmq.client.ConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Value("${rabbitmq.host}")
    String host;

    @Value("${rabbitmq.port}")
    String port;

    @Value("${rabbitmq.username}")
    String username;

    @Value("${rabbitmq.password}")
    String password;

    @Bean
    public ConnectionFactory getFactory(){
        ConnectionFactory factory=new ConnectionFactory();
        factory.setHost(host);
        factory.setPort(Integer.parseInt(port));
/*        factory.setPassword("admin");
        factory.setUsername("admin");*/
        return factory;
    }
}
