package com.dyh.config;


import com.dyh.pojo.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

//@Configuration代表这是一个配置类 = beans.xml
@Configuration
@ComponentScan("com.dyh.pojo")
public class MyConfig {

    //注册一个bean，就相当于我们之前写的一个bean标签
    //这个方法的名字，就相当于bean标签中的id属性
    //这个方法的返回值，就相当于bean标签中的class属性
    @Bean
    public User getUser() {
        //return就是返回要注入到bean的对象
        return new User();
    }
}
