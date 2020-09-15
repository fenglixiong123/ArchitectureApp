package com.flx.ark.java8.stream;


import com.flx.ark.java8.lambda.task.Employee;

import java.util.*;
import java.util.stream.Collectors;

/**
 *
 * 终结操作
 *
 * 规约与收集
 *
 * reduce 规约：将流中的元素反复结合起来
 *
 * collect 收集：将流转换为其他形式。接受一个Collector接口的实现，用于给Stream中的元素的方法做汇总
 *
 */
public class StreamAPI_six {

    static List<Employee> employees = Arrays.asList(

            new Employee("tom",18,5001.99, Employee.Status.FREE),
            new Employee("army",38,1002.99, Employee.Status.BUSY),
            new Employee("jack",8,2003.99,Employee.Status.VOCATION),
            new Employee("wang",16,7004.99, Employee.Status.FREE),
            new Employee("lucy",8,9005.99, Employee.Status.BUSY)

    );

    public static void main(String[] args) {

        testReduce();
        testCollect();

    }

    private static void testCollect() {

        List<Integer> list = Arrays.asList(1,2,3,4,5,6,7,8);
        List<String> names = employees.stream()
                .map(Employee::getName)
                .collect(Collectors.toList());

        Set<Integer> ages = employees.stream()
                .map(Employee::getAge)
                .collect(Collectors.toSet());

        HashSet<Integer> agess = employees.stream()
                .map(Employee::getAge)
                .collect(Collectors.toCollection(HashSet::new));
        //元素个数
        Long count = employees.stream()
                .collect(Collectors.counting());

        //平均值
        Double salary_avg = employees.stream()
                .collect(Collectors.averagingDouble(Employee::getSalary));

        //总和
        Double salary_sum = employees.stream()
                .collect(Collectors.summingDouble(Employee::getSalary));

        //最大值
        Optional<Employee> max = employees.stream()
                .collect(Collectors.maxBy((x,y)->Double.compare(x.getSalary(),y.getSalary())));

        //最小值
        Optional<Double> min = employees.stream()
                .map(Employee::getSalary)
                .collect(Collectors.minBy((x,y)->Double.compare(x,y)));

        //按照状态分组
        Map<Employee.Status, List<Employee>> collect = employees.stream()
                .collect(Collectors.groupingBy(Employee::getStatus));

        //多级分组
        Map<Employee.Status, Map<String, List<Employee>>> collect1 = employees.stream()
                .collect(Collectors.groupingBy(Employee::getStatus, Collectors.groupingBy(e -> {
                    if (e.getAge() <= 35) {
                        return "青年";
                    } else if (e.getAge() <= 50) {
                        return "中年";
                    } else {
                        return "老年";
                    }
                })));

        //分区
        Map<Boolean, List<Employee>> collect2 = employees.stream()
                .collect(Collectors.partitioningBy(e -> e.getSalary() > 8000));

        //综合
        DoubleSummaryStatistics summaryStatistics = employees.stream()
                .collect(Collectors.summarizingDouble(Employee::getSalary));
        summaryStatistics.getAverage();
        summaryStatistics.getCount();
        summaryStatistics.getMax();
        summaryStatistics.getMin();
        summaryStatistics.getSum();

        //链接
        String collect3 = employees.stream()
                .map(Employee::getName)
                .collect(Collectors.joining(","));
        String collect4 = employees.stream()
                .map(Employee::getName)
                .collect(Collectors.joining("==",",","==="));
    }

    private static void testReduce() {

        List<Integer> list = Arrays.asList(1,2,3,4,5,6,7,8);
        Integer sum = list.stream()
                .reduce(0,(x,y)->x+y);
//        System.out.println(sum);

        //初始值为0，反复加
        Optional<Double> salary = employees.stream()
                .map(Employee::getSalary)
                .reduce(Double::sum);
        System.out.println(salary.get());
    }

}
