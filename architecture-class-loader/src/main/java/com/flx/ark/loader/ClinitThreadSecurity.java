package com.flx.ark.loader;

/**
 * @Author Fenglixiong
 * @Create 2020/9/17 0:33
 * @Description
 * 证明每个类的clinit方法在虚拟机层面线程安全的
 **/
public class ClinitThreadSecurity {

    //初始化阶段执行的
    static {
        a = 20;
    }

    //准备阶段赋值默认值为0
    //初始化阶段赋值2
    private static int a = 2;

    public static void main(String[] args) {

        System.out.println(a);

    }

}
