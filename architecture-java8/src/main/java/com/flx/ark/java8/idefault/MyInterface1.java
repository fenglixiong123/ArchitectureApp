package com.flx.ark.java8.idefault;

public interface MyInterface1 {

    //默认实现方法
    default String getName(){
        return "MyInterface1";
    }

    static void show(){
        System.out.println("show MyInterface1");
    }

}
