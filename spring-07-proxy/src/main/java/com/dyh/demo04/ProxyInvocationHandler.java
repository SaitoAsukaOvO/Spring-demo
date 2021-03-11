package com.dyh.demo04;


import com.dyh.demo03.Rent;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

//自动生成代理类
public class ProxyInvocationHandler implements InvocationHandler {

    //被代理接口
    private Object target;

    public void setTarget(Object target) {
        this.target = target;
    }

    //得到生成代理类
    public Object getProxy() {
        return Proxy.newProxyInstance(this.getClass().getClassLoader(), target.getClass().getInterfaces(), this);
    }

    //处理代理实例，并返回结果
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        log(method.getName());
        return method.invoke(target, args);
    }

    public void log(String msg) {
        System.out.println("============using " + msg + " method==========");
    }
}
