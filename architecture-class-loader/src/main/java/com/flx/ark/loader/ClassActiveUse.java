package com.flx.ark.loader;

/**
 * @Author: Fenglixiong
 * @Date: 2020/10/22 15:55
 * @Description: 验证类的主动使用或导致类加载
 *
 * 类的六种主动使用：
 * 1.new直接使用
 * 2.访问某个类或者接口的静态变量，或者对改静态变量进行赋值操作
 * 3.调用静态方法
 * 4.反射某个类
 * 5.初始化一个子类
 * 6.启动类比如：java HelloWorld
 *
 * 几种不会导致初始化的易混淆情况：
 * 1.子类调用父类的静态变量
 * 2.调用类的常量,常量会在编译阶段放到常量池，不会初始化类
 * 3.调用结果无法直接算出的常量:x = new Random().nextInt(100)
 * 4.定义数组：Obj[] arrays = new Obj[10]
 */
public class ClassActiveUse {

    public static void main(String[] args) throws ClassNotFoundException {

        System.out.println("ClassActiveUse...");

        //1.new主动使用
//        new Left();

        //2.访问某个类的静态变量
//        System.out.println(Left.salary);

        //3.访问某个类的静态方法
//        Left.showSalary();

        //4.反射某个类
//        Class.forName("com.flx.ark.loader.ClassActiveUse.Left");

        //5.初始化一个子类
        new LeftSon();

        //6.启动类

    }

    static class Left{


        public static long salary = 1000;

        static {
            System.out.println("Left is run...");
        }

        public Left(){
            System.out.println("Left construct...");
        }

        public static void showSalary(){
            System.out.println("Salary is 10000 ");
        }

    }

    static class LeftSon extends Left{

        static {
            System.out.println("LeftSon is run ...");
        }

        public LeftSon(){
            System.out.println("LeftSon construct...");
        }

    }

}


