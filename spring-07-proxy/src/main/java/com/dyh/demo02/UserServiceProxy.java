package com.dyh.demo02;

public class UserServiceProxy implements UserService{

    private UserServiceImpl userService;

    public UserServiceProxy() {
    }

    public void setUserService(UserServiceImpl userService) {
        this.userService = userService;
    }

    public void add() {
        log("add");
        userService.add();
    }

    public void delete() {
        log("delete");
        userService.delete();
    }

    public void update() {
        log("update");
        userService.update();
    }

    public void query() {
        log("query");
        userService.query();
    }


    //log
    public void log(String msg) {
        System.out.println("[debug] " + msg + " method");
    }
}
