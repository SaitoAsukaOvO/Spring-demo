package com.dyh.pojo;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

//等价于 <bean id="user" class="com.dyh.pojo.User"/>
//@Component 组件
@Component
@Scope(value = "prototype")
public class User {
//    @Value("dyh")
    public String name;

    @Value("ddd")
    public void setName(String name) {
        this.name = name;
    }
}
