package com.flx.multi.thread.wangwenjun.juc.locks.condition;

import java.util.LinkedList;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.IntStream;

/**
 * @Author: Fenglixiong
 * @Date: 2021/3/22 0:05
 * @Description:
 */
public class ConditionDeep {

    public static void main(String[] args) {
        IntStream.range(1,6).forEach(i->new Thread(()->{
            for (;;){
                produce();
                try {
                    TimeUnit.SECONDS.sleep(2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"P-"+i).start());

        IntStream.range(1,6).forEach(i->new Thread(()->{
            for (;;){
                consume();
                try {
                    TimeUnit.SECONDS.sleep(3);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"C-"+i).start());
/*
        new Thread(()->{
            for (;;) {
                try {
                    TimeUnit.SECONDS.sleep(2);
                    System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
                    System.out.println("produce hasWaiters = " + lock.hasWaiters(produceCond));
                    System.out.println("consume hasWaiters = " + lock.hasWaiters(consumeCond));
                    System.out.println("produce getWaitQueueLength = " + lock.getWaitQueueLength(produceCond));
                    System.out.println("consume getWaitQueueLength = " + lock.getWaitQueueLength(consumeCond));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
*/

    }
    private final static ReentrantLock lock = new ReentrantLock();

    private final static Condition produceCond = lock.newCondition();
    private final static Condition consumeCond = lock.newCondition();

    private final static LinkedList<Long> dataList = new LinkedList<>();

    private final static int max_capacity = 100;

    private static Long data = 0L;

    public static void produce(){
        try{
            lock.lock();
            while (dataList.size()>=max_capacity){
                produceCond.await();
            }
            data ++;
            dataList.addLast(data);
            System.out.println(Thread.currentThread().getName()+" produce "+data);
            consumeCond.signalAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public static void consume(){
        try{
            lock.lock();
            while (dataList.isEmpty()){
                consumeCond.await();
            }
            Long data = dataList.removeFirst();
            System.out.println(Thread.currentThread().getName()+" consume "+data);
            produceCond.signalAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

}
