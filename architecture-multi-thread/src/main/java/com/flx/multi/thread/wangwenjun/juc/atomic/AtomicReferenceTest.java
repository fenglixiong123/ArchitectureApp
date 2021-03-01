package com.flx.multi.thread.wangwenjun.juc.atomic;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.concurrent.atomic.AtomicReference;

/**
 * @Author: Fenglixiong
 * @Date: 2021/2/26 18:17
 * @Description: AtomicReference将自定义对象定义成原子类型
 */
public class AtomicReferenceTest {

    public static void main(String[] args) {

        Simple jack = new Simple("Jack",12);
        Simple mary = new Simple("Marry",19);
        AtomicReference<Simple> reference = new AtomicReference<>(jack);
        System.out.println(reference);
        reference.compareAndSet(jack,mary);
        System.out.println(reference);
    }

    @Data
    @AllArgsConstructor
    static class Simple{
        private String name;
        private int age;
    }

}
