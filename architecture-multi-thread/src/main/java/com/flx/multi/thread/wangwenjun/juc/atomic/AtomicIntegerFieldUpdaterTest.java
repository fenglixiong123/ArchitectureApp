package com.flx.multi.thread.wangwenjun.juc.atomic;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

/**
 * @Author: Fenglixiong
 * @Date: 2021/3/3 16:57
 * @Description: 将对象中整型成员变量升级为原子操作数据
 * 此成员变量必须为 volatile修饰
 * 此成员变量必须为 非private修饰
 */
public class AtomicIntegerFieldUpdaterTest {

    public static void main(String[] args) throws InterruptedException {

        noSafeSum();
        safeSum();

    }

    /**
     * 采用update进行更新操作
     * @throws InterruptedException
     */
    private static void safeSum() throws InterruptedException {

        Student student = new Student("jack",0);

        AtomicIntegerFieldUpdater<Student> atomicStudent = AtomicIntegerFieldUpdater.newUpdater(Student.class,"age");
        final int THREAD_COUNT = 10;
        Thread[] threads = new Thread[THREAD_COUNT];
        for (int i=0;i<THREAD_COUNT;i++){
            threads[i] = new Thread(()->{
                for (int j = 0; j < 1000; j++) {
                    atomicStudent.incrementAndGet(student);
                }
                System.out.print(student.getAge()+" ");
            });
            threads[i].start();
        }
        for (int i = 0; i < THREAD_COUNT; i++) {
            threads[i].join();
        }

        System.out.println();
        System.out.println("safe age = "+student.getAge());
    }

    /**
     * 普通操作会出现线程不安全现象
     * @throws InterruptedException
     */
    private static void noSafeSum() throws InterruptedException {
        Student student = new Student("jack",0);
        final int THREAD_COUNT = 10;
        Thread[] threads = new Thread[THREAD_COUNT];
        for (int i=0;i<THREAD_COUNT;i++){
            threads[i] = new Thread(()->{
                for (int j = 0; j < 1000; j++) {
                    student.age++;
                }
                System.out.print(student.getAge()+" ");
            });
            threads[i].start();
        }
        for (int i = 0; i < THREAD_COUNT; i++) {
            threads[i].join();
        }

        System.out.println();
        System.out.println("unsafe age = "+student.getAge());
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    private static class Student {

        private String name;
        protected volatile int age;

    }

}
