package com.flx.multi.thread.wangwenjun.juc.atomic;

import lombok.Data;

import java.util.concurrent.atomic.AtomicStampedReference;

/**
 * @Author: Fenglixiong
 * @Date: 2021/3/1 15:43
 * @Description: 解决ABA问题，带版本标志的数据
 */
public class AtomicStampedReferenceTest1 {

    public static void main(String[] args) {


        Student jack = Student.of("jack",22);
        Student mary = Student.of("mary",18);

        //stamp版本起始为1
        AtomicStampedReference<Student> stampedReference = new AtomicStampedReference<>(jack,1);

        System.out.println(stampedReference.getReference());
        //期望版本为1，设置新版本为2
        stampedReference.compareAndSet(jack,mary,1,3);
        System.out.println(stampedReference.getReference());

        //这里有个小技巧
        //一个方法获取两个值，利用数组带一个值
        int[] stampHolder = new int[2];
        Student student = stampedReference.get(stampHolder);
        System.out.println(student);
        System.out.println(stampHolder[0]);


    }

    @Data
    private static class Student{
        private String name;
        private int age;

        public Student(String name, int age) {
            this.name = name;
            this.age = age;
        }

        static Student of(String name, int age){
            return new Student(name,age);
        }
    }

}
