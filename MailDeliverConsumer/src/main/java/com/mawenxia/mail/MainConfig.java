package com.mawenxia.mail;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * Created by mawenxia on 2017/5/22.
 */
@EnableWebMvc //启用springmvc
@Configuration //相当于xml配置类 让spring boot 启动时识别该配置类
@ComponentScan({"com.mawenxia.mail.*"})
@MapperScan(basePackages = "com.mawenxia.mail.mapper")
public class MainConfig {
}
