package com.flx.multi.thread.wangwenjun.juc.atomic;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author Fenglixiong
 * @Create 2020/12/8 22:38
 * @Description
 **/
public class AtomicIntegerTest2 {

    public static void main(String[] args) {

        /**
         * create
         */
        final AtomicInteger i = new AtomicInteger();
        System.out.println(i.get());
        i.set(10);
        System.out.println(i.get());
        int a = i.getAndAdd(6);
        System.out.println("a = "+a);
        System.out.println("i = "+i.get());
    }

}
