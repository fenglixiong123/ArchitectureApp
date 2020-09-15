package com.flx.ark.java8.lambda;

import com.flx.ark.java8.lambda.task.Employee;

import java.util.Comparator;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * 方法引用：若Lambda体中的方法已经有实现了可以使用方法引用
 *
 * 主要有三种语法格式
 *
 * 对象：：实例方法名
 *
 * 类：：静态方法名
 *
 * 类：：实例方法名
 *
 * 构造器引用
 *
 * ClassName::new
 *
 */
public class LambdaLearnC {

    public static void main(String[] args) {
        testOne();
        testTwo();
        testThree();
    }

    public static void testOne(){
        Consumer consumer = System.out::println;
        testPrint("hello",consumer);

    }

    private static void testPrint(String x,Consumer<String> consumer){
        consumer.accept(x);
    }

    private static void testTwo(){
        Employee employee = new Employee("sss",12,55);
        Supplier<Integer> supplier = employee::getAge;
        System.out.println(supplier.get());
    }

    private static void testThree(){
        Comparator<Integer> comparator = Integer::compare;
        int a = comparator.compare(2,3);
        System.out.println(a);
    }

    private static void testFour(){
        Supplier<Employee> supplier = Employee::new;
        supplier.get();
    }

}
