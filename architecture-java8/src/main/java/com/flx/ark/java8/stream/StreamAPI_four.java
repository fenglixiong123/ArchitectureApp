package com.flx.ark.java8.stream;



import com.flx.ark.java8.lambda.task.Employee;

import java.util.Arrays;
import java.util.List;

/**
 *
 * 中间操作
 *
 * 排序
 *
 * sorted()--自然排序
 *
 * sorted(Comparator)--定制排序
 *
 */
public class StreamAPI_four {


    static List<Employee> employees = Arrays.asList(

            new Employee("tom",18,5001.99),
            new Employee("army",38,1002.99),
            new Employee("jack",8,2003.99),
            new Employee("wang",16,7004.99),
            new Employee("lucy",8,9005.99)

    );

    public static void main(String[] args) {

        testSorted();
        testPersonSorted();

    }

    private static void testPersonSorted() {
        employees.stream()
                .sorted((x,y)->Integer.compare(x.getAge(),y.getAge()))
                .forEach(System.out::println);
        System.out.println("----------------------------------------------");
        employees.stream()
                .sorted((x,y)->x.getName().compareTo(y.getName()))
                .forEach(System.out::println);
    }

    private static void testSorted() {

        List<String> list = Arrays.asList("ddd","eee","ccc","aaa","bbb");
        list.stream()
                .sorted()
                .forEach(System.out::println);
    }

}
