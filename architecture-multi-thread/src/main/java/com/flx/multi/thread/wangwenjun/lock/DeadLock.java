package com.flx.multi.thread.wangwenjun.lock;

/**
 * @Author Fenglixiong
 * @Create 2020/8/26 21:46
 * @Description
 * 死锁
 * 多是两个线程去请求对方还没有释放的锁
 **/
public class DeadLock {

    public static void main(String[] args) {

        DeadLock deadLock = new DeadLock();

        new Thread(deadLock::m1,"T1").start();
        new Thread(deadLock::m2,"T2").start();
    }

    private final Object lock1 = new Object();
    private final Object lock2 = new Object();

    public void m1(){
        synchronized (lock1){
            System.out.println(Thread.currentThread().getName()+" m1 obtain lock1");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (lock2){
                System.out.println(Thread.currentThread().getName()+" m1 obtain lock2");
            }
        }
    }

    public void m2(){
        synchronized (lock2){
            System.out.println(Thread.currentThread().getName()+" m2 obtain lock2");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (lock1){
                System.out.println(Thread.currentThread().getName()+" m2 obtain lock1");
            }
        }
    }

}