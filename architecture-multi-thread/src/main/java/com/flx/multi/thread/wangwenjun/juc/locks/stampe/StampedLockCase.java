package com.flx.multi.thread.wangwenjun.juc.locks.stampe;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.StampedLock;
import java.util.stream.Collectors;

/**
 * @Author: Fenglixiong
 * @Date: 2021/3/22 1:06
 * @Description: 读乐观锁
 *
 * 和ReadWriteLock相比，写入的加锁是完全一样的，不同的是读取。
 *
 * 可见，StampedLock把读锁细分为乐观读和悲观读，能进一步提升并发效率。
 * 但这也是有代价的：
 * 一是代码更加复杂，
 * 二是StampedLock是不可重入锁，不能在一个线程中反复获取同一个锁。
 *
 * StampedLock还提供了更复杂的将悲观读锁升级为写锁的功能，
 * 它主要使用在if-then-update的场景：
 * 即先读，如果读的数据满足条件，就返回，如果读的数据不满足条件，再尝试写。
 */
public class StampedLockCase {

    public static void main(String[] args) {
        Runnable readTask = ()->{
            for (;;){
                read();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        Runnable writeTask = ()->{
            for (;;){
                write();
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        for (int i = 0; i < 9; i++) {
            executor.submit(readTask);
        }
        executor.submit(writeTask);
    }

    private final static ExecutorService executor = Executors.newFixedThreadPool(10);

    private final static StampedLock lock = new StampedLock();

    private final static List<Integer> dataList = new ArrayList<>();

    private final static Random random = new Random();

    /**
     * 采用乐观读锁--->如果写进行就转换为悲观锁
     *
     * 注意到首先我们通过tryOptimisticRead()获取一个乐观读锁，并返回版本号。接着进行读取，
     * 读取完成后，我们通过validate()去验证版本号，
     * 如果在读取过程中没有写入，版本号不变，验证成功，我们就可以放心地继续后续操作。
     * 如果在读取过程中有写入，版本号会发生变化，验证将失败。在失败的时候，我们再通过获取悲观读锁再次读取。
     * 由于写入的概率不高，程序在绝大部分情况下可以通过乐观读锁获取数据，极少数情况下使用悲观读锁获取数据。
     */
    public static void read(){
        long stamped = lock.tryOptimisticRead();//获取乐观锁
        //如果验证版本号发生变化，则获取悲观锁进行读取
        //否则直接进行读取操作
        if(!lock.validate(stamped)){
            System.out.println(Thread.currentThread().getName()+"other write thread working .");
            try {
                stamped = lock.readLock();
                System.out.println(Thread.currentThread().getName()+" get bad read lock.");
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                System.out.println(Thread.currentThread().getName()+" release bad read lock.");
                lock.unlockRead(stamped);
            }
        }
        Optional.of(
                dataList.stream().map(String::valueOf).collect(Collectors.joining("#", "R-", ""))
        ).ifPresent(System.out::println);
    }

    /**
     * 写锁一直是悲观锁
     */
    public static void write(){
        long stamped = -1;
        try{
            stamped = lock.writeLock();
            System.out.println(Thread.currentThread().getName()+" write get lock!");
            dataList.add(random.nextInt(9));
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            System.out.println(Thread.currentThread().getName()+" write release lock!");
            lock.unlockWrite(stamped);
        }
    }

}
