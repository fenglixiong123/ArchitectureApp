package com.flx.multi.thread.wangwenjun.juc.atomic.cases;

import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

/**
 * @Author: Fenglixiong
 * @Date: 2021/3/4 10:52
 * @Description: 通过无锁的方式实现syn的功能，多线程更新整型变量
 */
public class FieldUpdater {

    private volatile int count;

    private AtomicIntegerFieldUpdater<FieldUpdater> updater = AtomicIntegerFieldUpdater.newUpdater(FieldUpdater.class,"count");

    public boolean update(int newValue){
        return updater.compareAndSet(this,count,newValue);
    }

    public int get(){
        return count;
    }

    public static void main(String[] args) {
        FieldUpdater updater = new FieldUpdater();
        updater.update(10);
        System.out.println(updater.get());
    }

}
