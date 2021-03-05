package com.dyh.dao;

public class MysqlImpl implements UserDao{

    @Override
    public void getUser() {
        System.out.println("Mysql user data");
    }
}
