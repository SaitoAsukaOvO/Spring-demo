package com.dyh.demo04;

import com.dyh.demo02.UserService;
import com.dyh.demo02.UserServiceImpl;

public class Client {
    public static void main(String[] args) {
        //真实角色
        UserServiceImpl userService = new UserServiceImpl();

        //代理角色，不存在
        ProxyInvocationHandler handler = new ProxyInvocationHandler();

        //设置需要代理的对象
        handler.setTarget(userService);

        //动态生成代理类
        UserService proxy = (UserService) handler.getProxy();

        proxy.query();
    }

}
