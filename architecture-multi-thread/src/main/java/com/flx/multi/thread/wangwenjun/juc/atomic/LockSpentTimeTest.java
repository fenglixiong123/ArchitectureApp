package com.flx.multi.thread.wangwenjun.juc.atomic;

import sun.misc.Unsafe;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Author: Fenglixiong
 * @Date: 2021/3/4 16:52
 * @Description: 各种锁花费时间测试
 */
public class LockSpentTimeTest {

    public static void main(String[] args) throws InterruptedException, NoSuchFieldException {

        ExecutorService executorService = Executors.newFixedThreadPool(1000);
//        Counter counter = new StupidCounter();//180
//        Counter counter = new LockCounter();//340
//        Counter counter = new SynCounter();//600
//        Counter counter = new AtomicCounter();//370
        Counter counter = new CasCounter();//1100
        long start = System.currentTimeMillis();
        for (int i=0;i<1000;i++){
            executorService.submit(new CounterRunnable(counter,10000));
        }
        executorService.shutdown();
        executorService.awaitTermination(1, TimeUnit.HOURS);
        long end = System.currentTimeMillis();
        System.out.println("counter result = "+counter.getCounter());
        System.out.println("Time passed in ms : "+(end-start));
    }

    interface Counter{

        void increment();

        long getCounter();
    }

    //200
    static class StupidCounter implements Counter{

        private long counter = 0;

        @Override
        public void increment() {
            counter++;
        }

        @Override
        public long getCounter() {
            return counter;
        }
    }

    //350
    static class LockCounter implements Counter{

        private long counter = 0;

        private final Lock lock = new ReentrantLock();

        @Override
        public void increment() {
            try {
                lock.lock();
                counter++;
            }finally {
                lock.unlock();
            }
        }

        @Override
        public long getCounter() {
            return counter;
        }
    }

    //600
    static class SynCounter implements Counter{

        private long counter = 0;

        @Override
        public synchronized void increment() {
            counter++;
        }

        @Override
        public long getCounter() {
            return counter;
        }
    }

    //340
    static class AtomicCounter implements Counter{

        private AtomicLong counter = new AtomicLong(0);

        @Override
        public void increment() {
            counter.incrementAndGet();
        }

        @Override
        public long getCounter() {
            return counter.get();
        }
    }

    //1100
    static class CasCounter implements Counter{

        private volatile long counter = 0;
        private Unsafe unsafe;
        private long offset;

        CasCounter() throws NoSuchFieldException {
            unsafe = UnsafeTest.getUnsafe();
            offset = unsafe.objectFieldOffset(CasCounter.class.getDeclaredField("counter"));
        }

        @Override
        public void increment() {
            long current = counter;
            while (!unsafe.compareAndSwapLong(this,offset,current,current+1)){
                current = counter;
            }
        }

        @Override
        public long getCounter() {
            return counter;
        }
    }

    static class CounterRunnable implements Runnable{

        private final Counter counter;
        private final int num;

        public CounterRunnable(Counter counter, int num) {
            this.counter = counter;
            this.num = num;
        }

        @Override
        public void run() {
            for (int i = 0; i < num; i++) {
                counter.increment();
            }
        }
    }

}
