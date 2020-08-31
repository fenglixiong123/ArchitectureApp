package com.flx.multi.thread.base.lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Author Fenglixiong
 * @Create 2020/8/11 0:13
 * @Description
 * 手动上锁，手动解锁，灵活性高
 * 繁琐，效率不高
 **/
public class LockClass {

    public static void main(String[] args) {

        Lock lock = new ReentrantLock();

        Condition condition = lock.newCondition();

        ThreadTask task = new ThreadTask(lock,condition);

        Thread threadA = new Thread(task);
        Thread threadB = new Thread(task);

        threadA.start();
        threadB.start();

    }

}


class ThreadTask implements Runnable{

    private Lock lock;
    private Condition condition;

    private int count = 100;

    public ThreadTask(Lock lock,Condition condition) {
        this.lock = lock;
        this.condition = condition;
    }

    @Override
    public void run() {
        sale();
    }

    public void sale(){
        try {
            lock.lock();
            System.out.println(Thread.currentThread().getName()+"....."+count);
            Thread.sleep(3000);
            count = count + 100;
            System.out.println(Thread.currentThread().getName()+"....."+count);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }

}
