package com.flx.multi.thread.wangwenjun.juc.atomic;

import java.util.concurrent.atomic.AtomicIntegerArray;

/**
 * @Author: Fenglixiong
 * @Date: 2021/3/2 10:42
 * @Description:
 */
public class AtomicIntegerArrayTest {

    public static void main(String[] args) {

        AtomicIntegerArray integerArray = new AtomicIntegerArray(5);
        integerArray.set(1,5);
        System.out.println(integerArray.get(1));
        integerArray.getAndSet(1,8);
    }

}