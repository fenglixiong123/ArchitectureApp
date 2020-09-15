package com.flx.ark.java8.idefault;

public class RunTest {

    /**
     * 类优先原则，default接口中默认方法优先级最低
     *
     * 冲突：
     *
     * 1.当同时继承和实现接口中都有同样的方法
     *      优先继承类中
     *
     * 2.当同时实现两个接口中都有相同的默认方法
     *      报错
     *
     * @param args
     */
    public static void main(String[] args) {

        SubClass subClass = new SubClass();

        String name = subClass.getName();

        System.out.println(name);

        MyInterface1.show();

    }

}
