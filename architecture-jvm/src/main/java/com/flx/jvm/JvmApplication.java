package com.flx.jvm;

/**
 * @Author: Fenglixiong
 * @Date: 2020/8/26 17:05
 * @Description:
 */
public class JvmApplication {

    public static void main(String[] args) {
        System.out.println(m1());
        System.out.println(m2());
    }

    public static int m1(){
        int a = 2;
        int b = 3;
        int c = a+b;
        return c;
    }

    public static int m2(){
        int a = 2;
        int b = 3;
        int c = a-b;
        return c;
    }

}
