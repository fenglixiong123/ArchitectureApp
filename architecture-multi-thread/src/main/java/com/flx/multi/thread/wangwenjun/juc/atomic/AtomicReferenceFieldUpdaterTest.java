package com.flx.multi.thread.wangwenjun.juc.atomic;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;

/**
 * @Author: Fenglixiong
 * @Date: 2021/3/3 18:54
 * @Description: 对对象指定字段进行原子化更新操作
 * 使用场景：
 * 1.想让类的属性操作具备原子性
 * 2.不想使用锁（包含显示锁或者重量级锁synchronized）
 * 3.大量需要原子类型修饰的对象，相比较耗费内存
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
