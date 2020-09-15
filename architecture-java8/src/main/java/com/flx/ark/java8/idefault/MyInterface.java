package com.flx.ark.java8.idefault;

public interface MyInterface {

    //默认实现方法
    default String getName(){
        return "MyInterface";
    }

    public static void show(){
        System.out.println("show MyInterface");
    }

}
