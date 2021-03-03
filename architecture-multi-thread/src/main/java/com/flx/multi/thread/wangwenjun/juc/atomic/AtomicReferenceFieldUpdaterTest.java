package com.flx.multi.thread.wangwenjun.juc.atomic;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;

/**
 * @Author: Fenglixiong
 * @Date: 2021/3/3 18:54
 * @Description: 将对象指定字段进行原子化更新操作
 */
public class AtomicReferenceFieldUpdaterTest {

    public static void main(String[] args) {

        Student student = new Student("jack",22);
        AtomicReferenceFieldUpdater<Student,String> referenceUpdater = AtomicReferenceFieldUpdater.newUpdater(Student.class,String.class,"name");
        boolean result = referenceUpdater.compareAndSet(student, "jack", "marry");
        System.out.println(result);
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    static class Student{
        volatile String name;
        private int age;
    }
}
