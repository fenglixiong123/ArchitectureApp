package com.flx.ark.java8.stream;


import com.flx.ark.java8.lambda.task.Employee;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

/**
 *
 * 1.创建stream
 *
 * 2.中间操作
 *
 * 3.终止操作
 *
 */
public class StreamAPI_one {


    public static void main(String[] args) {

        testOne();

    }

    /**
     * 1.创建流
     *
     * 可以通过Collection系列集合提供的stream
     * 或者parallelStream
     */
    private static void testOne(){
        //1.集合流
        List<String> list = new ArrayList<>();
        Stream<String> stream1 = list.stream();

        //2.数组流
        Employee[] employees = new Employee[10];
        Stream<Employee> stream2 = Arrays.stream(employees);

        //3.
        Stream<String> stream3 = Stream.of("aa","bb","cc");

        //4.迭代创建无限流
        Stream<Integer> stream4 = Stream.iterate(0,x->x+2);
        stream4.limit(10)
                .forEach(System.out::println);

        //5.生成创建无限流
        Stream<Double> stream5 = Stream.generate(()->Math.random());
        stream5.limit(10).forEach(System.out::println);
    }



}
