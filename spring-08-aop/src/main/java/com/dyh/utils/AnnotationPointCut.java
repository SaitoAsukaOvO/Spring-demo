package com.dyh.utils;

//注解实现aop

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect // 标注这个类是切面
public class AnnotationPointCut {

    @Before("execution(* com.dyh.service.UserServiceImpl.*(..))")
    public void before() {
        System.out.println("=========before the method=========");
    }

    @After("execution(* com.dyh.service.UserServiceImpl.*(..))")
    public void after() {
        System.out.println("=========after the method==========");
    }

    //在环绕增强中我们可以给定一个参数，代表我们要获取处理切入的点
    @Around("execution(* com.dyh.service.UserServiceImpl.*(..))")
    public void around(ProceedingJoinPoint joinPoint) throws Throwable {
        System.out.println(">>>>>>>>>>before around<<<<<<<<<<<");
        Signature signature = joinPoint.getSignature();
        Object proceed = joinPoint.proceed();
        System.out.println(signature.toString() + " " + proceed);
        System.out.println(">>>>>>>>>>after around<<<<<<<<<<<<");
    }
}
