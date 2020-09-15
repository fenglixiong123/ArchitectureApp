package com.flx.ark.java8.lambda.task;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class TaskAnswer {

    public static void main(String[] args) {


        testOne();
        testTwo();
        testThree();

    }

    private static void testThree() {

        long var1 = compute(5L,6L,(x,y)->x+y);
        System.out.println(var1);

        long var2 = compute(5L,6L,(x,y)->x*y);
        System.out.println(var2);

    }

    private static long compute(long t1,long t2,ComputeLong<Long,Long> computeLong){
        return computeLong.compute(t1,t2);
    }

    private static void testTwo() {

        String str1 = strHandler("ashdashADsdsHsd",e->e.toUpperCase());
        System.out.println(str1);

        String str2 = strHandler("ashdashADsdsHsd",e->e.substring(2,5));
        System.out.println(str2);

    }

    private static String strHandler(String source,StringToUpper stringToUpper){
        return stringToUpper.getValue(source);
    }

    private static void testOne() {
        List<Employee> employees = Arrays.asList(

                new Employee("王小明1",18,5001.99),
                new Employee("王小明2",38,1002.99),
                new Employee("yaml",8,2003.99),
                new Employee("王小明4",16,7004.99),
                new Employee("mare",8,9005.99)

        );

        Collections.sort(employees,(o1,o2)->{
            if(o1.getAge()!=o2.getAge()){
                return Integer.compare(o1.getAge(),o2.getAge());
            }else {
                return o1.getName().compareTo(o2.getName());
            }
        });

        employees.forEach(System.out::println);
    }

}
