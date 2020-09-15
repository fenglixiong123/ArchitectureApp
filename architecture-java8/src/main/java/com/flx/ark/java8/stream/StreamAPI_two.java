package com.flx.ark.java8.stream;


import com.flx.ark.java8.lambda.task.Employee;

import java.util.Arrays;
import java.util.List;

/**
 *
 * 中间操作
 *
 * 筛选与切片
 *
 * filter--接受lambda表达式，从流中排除某些元素
 * limit--截断流，使得其元素个数不超过该认定数量
 * skip--跳过元素，返回一个扔掉前N个元素的流，若流中元素不足n个则返回一个空流，与limit互补
 * distinct--筛选，通过流元素的hashcode和equals方法去重复元素
 *
 */
public class StreamAPI_two {

    static List<Employee> employees = Arrays.asList(

            new Employee("王小明1",18,5001.99),
            new Employee("王小明2",38,1002.99),
            new Employee("王小明3",8,2003.99),
            new Employee("王小明4",16,7004.99),
            new Employee("王小明5",8,9005.99)

    );


    public static void main(String[] args) {

        System.out.println("------------------------------------------------------------------");
        System.out.println("filter--接受lambda表达式，从流中排除某些元素");
        testFilter();
        System.out.println("------------------------------------------------------------------");
        System.out.println("limit--截断流，使得其元素个数不超过该认定数量");
        testLimit();
        System.out.println("------------------------------------------------------------------");
        System.out.println("skip--跳过元素，返回一个扔掉前N个元素的流，若流中元素不足n个则返回一个空流，与limit互补");
        testSkip();
        System.out.println("------------------------------------------------------------------");
        System.out.println("distinct--筛选，通过流元素的hashcode和equals方法去重复元素");
        testDistinct();
    }

    private static void testDistinct() {

        List<String> list = Arrays.asList("aa","bb","aa","ccc","bb","zzz","ccc");
        list.stream()
                .distinct()
                .forEach(System.out::println);

    }

    private static void testSkip() {

        employees.stream()
                .filter(x->x.getAge()>=16)
                .skip(2)
                .forEach(System.out::println);

    }

    private static void testLimit() {

        employees.stream()
                .filter(x->x.getSalary()>5000)
                .limit(2)
                .forEach(System.out::println);
    }

    private static void testFilter() {

        //惰性求值
        //中间操作不会做任何操作，直到终止操作才会一次性执行全部操作
        employees.stream()
                .filter(x->x.getAge()>=16)
//                .limit(1)
                .forEach(System.out::println);

    }

}
