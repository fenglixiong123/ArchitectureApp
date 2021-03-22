package com.flx.multi.thread.wangwenjun.juc.locks.stampe;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.StampedLock;
import java.util.stream.Collectors;

/**
 * @Author: Fenglixiong
 * @Date: 2021/3/22 1:06
 * @Description: 带时间戳的锁,解决大量读线程因为写线程而导致的个个加锁的情况
 *
 * ReentrantLock VS Synchronized
 * lock有更多api跟多方法，syn只能单独使用
 * lock使用更加灵活，而且可以进行扩展（继承）
 *
 * 情况：
 * 100个thread
 * 99 thread need read lock
 * 1  thread need write lock
 * 比读写锁更加有优势
 */
public class StampedLockApi {

    public static void main(String[] args) {
        Runnable readTask = ()->{
            for (;;){
                read();
            }
        };
        Runnable writeTask = ()->{
            for (;;){
                write();
            }
        };
        executor.submit(readTask);
        executor.submit(readTask);
        executor.submit(readTask);
        executor.submit(readTask);
        executor.submit(readTask);
        executor.submit(readTask);
        executor.submit(readTask);
        executor.submit(readTask);
        executor.submit(readTask);
        executor.submit(writeTask);
    }

    private final static ExecutorService executor = Executors.newFixedThreadPool(10);

    private final static StampedLock lock = new StampedLock();

    private final static List<Long> dataList = new ArrayList<>();

    /**
     * 采用悲观读锁--->效果同reentrantLock
     */
    public static void read(){
        long stamped = -1;
        try{
            stamped = lock.readLock();
            Optional.of(
                    dataList.stream().map(String::valueOf).collect(Collectors.joining("#", "R-", ""))
            ).ifPresent(System.out::println);
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlockRead(stamped);
        }
    }

    /**
     * 采用悲观写锁--->效果同reentrantLock
     */
    public static void write(){
        long stamped = -1;
        try{
            stamped = lock.writeLock();
            dataList.add(System.currentTimeMillis());
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlockWrite(stamped);
        }
    }

}
