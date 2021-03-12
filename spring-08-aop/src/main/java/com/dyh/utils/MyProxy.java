package com.dyh.utils;

public class MyProxy {

    public void before() {
        System.out.println("===============before the method===================");
    }

    public void after() {
        System.out.println("================after the method====================");
    }
}
