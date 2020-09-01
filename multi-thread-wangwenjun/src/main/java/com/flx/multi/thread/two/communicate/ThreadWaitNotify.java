package com.flx.multi.thread.two.communicate;

/**
 * @Author Fenglixiong
 * @Create 2020/8/26 23:53
 * @Description
 * 线程等待和唤醒
 * 线程等待必须由另一个线程来唤醒自己
 * notify()
 * 只会唤醒一个等待锁的线程
 * notifyAll()
 * 会唤醒所有等待这个锁的线程
 **/
public class ThreadWaitNotify {

    public static void main(String[] args) {

        ThreadWaitNotify waitNotify = new ThreadWaitNotify();

        new Thread(){
            @Override
            public void run() {
                waitNotify.waitLock();
            }
        }.start();

        new Thread(){
            @Override
            public void run() {
                waitNotify.waitLock();
            }
        }.start();

        new Thread(){
            @Override
            public void run() {
                try {
                    Thread.sleep(5_000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                waitNotify.notifyLock();
            }
        }.start();

    }

    private final Object lock = new Object();

    public void waitLock(){
        synchronized (lock){
            try {
                System.out.println(Thread.currentThread().getName()+" I am wait!");
                lock.wait();
                Thread.sleep(5_000);
                System.out.println(Thread.currentThread().getName()+" I am notify!");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void notifyLock(){
        synchronized (lock){
            System.out.println(Thread.currentThread().getName()+" try to notify!");
            lock.notifyAll();
            System.out.println(Thread.currentThread().getName()+" finished notify!");
        }
    }

}
