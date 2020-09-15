package com.flx.ark.java8.lambda;

import com.flx.ark.java8.lambda.service.MultiParamHasReturn;
import com.flx.ark.java8.lambda.service.NoneParamNoReturn;
import com.flx.ark.java8.lambda.service.OneParamNoReturn;

/**
 * Lambda表达式基础语法：java8中引入一个新的操作符合->该操作符将表达式拆分为两部分
 *
 * 左侧：表达式参数列表
 * 右侧：表达式所执行功能
 *
 * 格式一：五参数无返回值
 *      ()->System.out.println();
 *
 * 格式二：有一个参数并且无返回值(小括号可以省略不写)
 *      (e)->System.out.println(e);
 *
 * 格式三：有两个以上参数，并且多条语句，有返回值
 *      (a,b)->{
 *          System.out.println("a:"+a+"b:"+b);
 *          return a+b;
 *      }
 * 格式四：如果只有一条语句有返回值，那么大括号和return都可以省略
 *
 * Lambda表达式需要函数式接口的支持
 *
 * 函数式接口：接口中只有一个抽象方法的接口，称为函数式接口
 * 可以用@FunctionInterface修饰可以检查是否是函数式接口
 */
public class LambdaLearnA {

    public static void main(String[] args) {

        testNoneParamNoReturn();
        testOneParamNoReturn();
        testMultiParamHasReturn();
    }

    /**
     * 无参数无返回值
     */
    private static void testNoneParamNoReturn() {

        NoneParamNoReturn noneParam = ()-> {
            System.out.println("Hello!");
            System.out.println("World!");
        };
        noneParam.method();

    }

    /**
     * 一个参数无返回值
     */
    private static void testOneParamNoReturn(){

        OneParamNoReturn oneParamNoReturn = (e)->System.out.println(e);
        oneParamNoReturn.oneParamNoReturn("jack");

    }

    /**
     * 多个参数多条语句多个返回值
     */
    private static void testMultiParamHasReturn(){
        MultiParamHasReturn multiParamHasReturn = (a, b)->{
            System.out.println("a:"+a+",b:"+b);
            return a+b;
        };
        int sum = multiParamHasReturn.add(2, 3);
        System.out.println("sum:"+sum);
    }

    /**
     * 多个参数多条语句多个返回值
     */
    private static void testMultiParamHasReturn2(){
        MultiParamHasReturn multiParamHasReturn = (a,b)-> a+b;
        int sum = multiParamHasReturn.add(2, 3);
        System.out.println("sum:"+sum);
    }
}
