package com.flx.ark.loader.custom;

/**
 * @Author: Fenglixiong
 * @Date: 2020/10/26 17:35
 * @Description:
 */
public class MyObject {

    static {
        System.out.println("MyObject static block .............");
    }

    public String hello(){
        System.out.println("Hello,World !");
        return "Hello,World !";
    }

}
