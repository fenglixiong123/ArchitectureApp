package com.flx.ark.loader;

/**
 * @Author Fenglixiong
 * @Create 2020/9/14 22:14
 * @Description
 * 常量会在编译阶段放到常量池，不会初始化类
 * 定义引用数组，不会初始化类
 * 通过子类访问父类的静态变量，不会导致子类的初始化
 **/
public class Singleton {

    static {
        System.out.println("I am initialed ! ");
    }

    private static Singleton instance = new Singleton();

    public static int x = 0;
    public static int y;


    /**
     * 链接-->准备过程给静态变量分配内存，并赋值默认值
     * instance = null;
     * int x = 0;
     * int y = 0;
     * 初始化
     * instance = new Instance();//x = 1; y = 1
     * int x = 0;
     * int y = 1;
     */

    private Singleton(){
        System.out.println("construct x = "+x+",y = "+y);
        x++;
        y++;
        System.out.println("construct x = "+x+",y = "+y);
    }

    public static Singleton getInstance(){
        return instance;
    }

    public static void main(String[] args) {
        Singleton singleton = Singleton.getInstance();
        System.out.println(Singleton.x);
        System.out.println(Singleton.y);
    }

}
