package com.dyh.demo03;

public class test {
    String name = "Mick";

    public void print(String name) {
        System.out.println("类中的属性 name=" + this.name);
        System.out.println("局部传参的属性=" + name);
    }

    public static void main(String[] args) {
        test tt = new test();
        tt.print("Orson");
    }
}
