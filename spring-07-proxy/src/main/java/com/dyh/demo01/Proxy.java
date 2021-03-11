package com.dyh.demo01;

public class Proxy implements Rent{

    private Host host;

    public Proxy() {
    }

    public Proxy(Host host) {
        this.host = host;
    }

    public void rent() {
        visit();
        host.rent();
        sign();
        pay();
    }


    public void visit() {
        System.out.println("visit the house");
    }

    public void pay() {
        System.out.println("Pay the fee");
    }

    public void sign() {
        System.out.println("Sign a contract");
    }
}
