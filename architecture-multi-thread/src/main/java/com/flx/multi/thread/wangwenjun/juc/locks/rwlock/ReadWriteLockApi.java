package com.flx.multi.thread.wangwenjun.juc.locks.rwlock;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @Author: Fenglixiong
 * @Date: 2021/3/21 14:56
 * @Description: 读写锁,只有读读可以不受锁的影响
 * 凡是有写得操作都不能同时获得锁
 */
public class ReadWriteLockApi {

    private final static ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock(true);

    private final static Lock readLock = readWriteLock.readLock();
    private final static Lock writeLock = readWriteLock.writeLock();

    private final static List<Long> data = new ArrayList<>();

    public static void main(String[] args) {
        Thread t1 = new Thread(ReadWriteLockApi::read);
        t1.start();
        Thread t2 = new Thread(ReadWriteLockApi::write);
        t2.start();
    }

    public static void write(){
        try{
            writeLock.lock();
            System.out.println(Thread.currentThread().getName()+" get lock");
            data.add(System.currentTimeMillis());
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            writeLock.unlock();
            System.out.println(Thread.currentThread().getName()+" release lock");
        }
    }

    public static void read(){
        try{
            readLock.lock();
            System.out.println(Thread.currentThread().getName()+" get lock");
            Thread.sleep(1000);
            data.forEach(System.out::println);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            readLock.unlock();
            System.out.println(Thread.currentThread().getName()+" release lock");
        }
    }

}
