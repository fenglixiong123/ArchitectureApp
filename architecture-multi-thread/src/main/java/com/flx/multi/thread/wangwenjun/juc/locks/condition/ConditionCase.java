package com.flx.multi.thread.wangwenjun.juc.locks.condition;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Author: Fenglixiong
 * @Date: 2021/3/21 19:28
 * @Description:
 */
public class ConditionCase {

    public static void main(String[] args) {

        new Thread(()->{
            while (true){
                product();
            }
        },"P1").start();
        new Thread(()->{
            while (true){
                product();
            }
        },"P2").start();
        new Thread(()->{
            while (true){
                consume();
            }
        },"C1").start();
        new Thread(()->{
            while (true){
                consume();
            }
        },"C2").start();
    }

    private final static ReentrantLock lock = new ReentrantLock();

    private final static Condition condition = lock.newCondition();

    private static int data = 0;

    private static volatile boolean isProduced = false;

    public static void product(){
        try {
            lock.lock();
            //wait会释放锁（也会释放cup执行权）
            //此处不能换成if,因为被唤醒后需要再次检查isProduced变量是否已经生产
            while (isProduced){
                condition.await();
            }
            Thread.sleep(2000);
            data ++;
            System.out.println(Thread.currentThread().getName()+" produce "+data);
            isProduced = true;
            condition.signalAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public static void consume(){
        try {
            lock.lock();
            while (!isProduced){
                condition.await();
            }
            Thread.sleep(1000);
            System.out.println(Thread.currentThread().getName()+" consumer "+data);
            isProduced = false;
            condition.signalAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }



}
