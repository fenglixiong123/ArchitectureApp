package com.flx.ark.java8.stream;

import com.flx.noodle.java8.lambda.task.Employee;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

/**
 *
 * 中间操作
 *
 * 映射
 *
 * map--接受lambda，将元素转换成其他形式或者提取信息。
 * 接受一个函数作为参数，该函数会被应用到每个元素，并映射成一个新的元素
 *
 * flatMap--接受一个函数作为参数，将流中的每个值都转换成另一个流，然后把所有流链接成一个流
 *
 */
public class StreamAPI_three {

    static List<Employee> employees = Arrays.asList(

            new Employee("王小明1",18,5001.99),
            new Employee("王小明2",38,1002.99),
            new Employee("王小明3",8,2003.99),
            new Employee("王小明4",16,7004.99),
            new Employee("王小明5",8,9005.99)

    );

    public static void main(String[] args) {

        testMap();
        System.out.println("++++++++++++++++++++++++++++++++++++++++++++");
        testFlatMap();
    }

    private static void testFlatMap() {

        List<String> list = Arrays.asList("aaa","bbb","ccc","ddd","eee");
        list.stream()
                .flatMap(StreamAPI_three::filterCharacter)
                .forEach(System.out::print);

    }

    private static Stream<Character> filterCharacter(String source){
        List<Character> list = new ArrayList<>();
        for (Character ch:source.toCharArray()){
            list.add(ch);
        }
        return list.stream();
    }

    private static void testMap() {

        List<String> list = Arrays.asList("aaa","bbb","ccc","ddd","eee");

        list.stream()
                .map(x->x.toUpperCase())
                .forEach(System.out::println);


        employees.stream()
                .map(e->e.getName())//产生新流
                .forEach(System.out::println);

    }


}
