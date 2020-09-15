package com.flx.ark.java8.stream;

import com.flx.ark.java8.lambda.task.Employee;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 *
 * 终止操作
 *
 * 查找与匹配
 * allMatch--检查是否匹配所有元素
 * anyMatch--检查是否至少有一个匹配
 * noneMatch--检查是否没有匹配所有元素
 * findFirst--返回第一个元素
 * findAny--返回任意元素
 * count--返回元素个数
 * max--返回流中最大的
 * min--返回流中最小的
 *
 *
 */
public class StreamAPI_five {

    static List<Employee> employees = Arrays.asList(

            new Employee("tom",18,5001.99, Employee.Status.FREE),
            new Employee("army",38,1002.99, Employee.Status.BUSY),
            new Employee("jack",8,2003.99,Employee.Status.VOCATION),
            new Employee("wang",16,7004.99, Employee.Status.FREE),
            new Employee("lucy",8,9005.99, Employee.Status.BUSY)

    );

    public static void main(String[] args) {

        testAllMatch();
        testAnyMatch();
        testNoneMatch();
        testFindFirst();
        testFindAny();
        testCount();
        testMax();
        testMin();
    }

    private static void testMin() {
        Optional<Employee> min = employees.stream()
                .min((x, y) -> Integer.compare(x.getAge(), y.getAge()));
        System.out.println("testMin:"+min.get());
    }

    private static void testMax() {
        Optional<Employee> min = employees.stream()
                .max((x, y) -> Integer.compare(x.getAge(), y.getAge()));
        System.out.println("testMax:"+min.get());
    }

    private static void testCount() {
        long count = employees.stream()
                .count();
        System.out.println("testCount:"+count);
    }

    private static void testFindAny() {
        Optional<Employee> any = employees.stream()
                .findAny();
        System.out.println("testFindAny:"+any.get());
    }

    private static void testFindFirst() {
        Optional<Employee> ret = employees.stream()
                .sorted((x,y)->Double.compare(x.getSalary(),y.getSalary()))
                .findFirst();
        System.out.println("testFindFirst:"+ret.get());
    }

    private static void testNoneMatch() {
        boolean ret = employees.stream()
                .noneMatch(e->e.getSalary()<0);
        System.out.println("testNoneMatch:"+ret);
    }

    private static void testAnyMatch() {
        boolean ret = employees.stream()
                .anyMatch(e->e.getStatus().equals(Employee.Status.BUSY));
        System.out.println("testAnyMatch:"+ret);
    }

    private static void testAllMatch() {
        boolean ret = employees.stream()
                .allMatch(e->e.getStatus().equals(Employee.Status.BUSY));
        System.out.println("testAllMatch:"+ret);
    }



}
