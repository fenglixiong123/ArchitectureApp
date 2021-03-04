package com.flx.multi.thread.wangwenjun.juc.atomic;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author Fenglixiong
 * @Create 2020/12/8 22:38
 * @Description Atomic整型原子操作类
 **/
public class AtomicIntegerTest {

    public static void main(String[] args) throws InterruptedException, NoSuchFieldException {

        /**
         * create
         */
        final AtomicInteger i = new AtomicInteger();
        System.out.println(i.get());
        i.set(10);
        System.out.println(i.get());
        int a = i.getAndAdd(6);
        System.out.println("a = "+a);
        System.out.println("i = "+i.get());

        //打印AtomicInteger的字段值
        System.out.println(AtomicInteger.class.getDeclaredField("value"));

        //不安全的操作方法
        unSafeSum();
        safeSum();

    }

    private static int count = 0;

    /**
     * 不安全 结果不一定是10000
     */
    private static void unSafeSum() throws InterruptedException {
        Thread[] ts = new Thread[10];
        for (int i=0;i<10;i++){
            ts[i] = new Thread(()->{
                for (int c=0;c<1000;c++){
                    count ++;
                }
                System.out.print(count+" ");
            });
            ts[i].start();
        }
        for (int i=0;i<10;i++){
            ts[i].join();
        }

        System.out.println();
        System.out.println("count = "+count);

    }

    /**
     * 不保证原子性
     */
    //private static volatile int value = 0;
    private static volatile AtomicInteger value = new AtomicInteger(0);

    @SuppressWarnings("all")
    public static void safeSum() throws InterruptedException {

        /**
         * value = value + 1
         * (1) get value from main memory to local memory
         * (2) add 1 => x
         * (3) assign the value to x
         * (4) flush to main memory
         */
        Thread t1 = new Thread(){
            @Override
            public void run() {
                int x = 0;
                while (x<500){
                    int tem = value.addAndGet(1);
                    System.out.println(Thread.currentThread().getName()+":"+tem);
                    x++;
                }
            }
        };

        Thread t2 = new Thread(){
            @Override
            public void run() {
                int x = 0;
                while (x<500){
                    int tem = value.addAndGet(1);
                    System.out.println(Thread.currentThread().getName()+":"+tem);
                    x++;
                }
            }
        };

        Thread t3 = new Thread(){
            @Override
            public void run() {
                int x = 0;
                while (x<500){
                    int tem = value.addAndGet(1);
                    System.out.println(Thread.currentThread().getName()+":"+tem);
                    x++;
                }
            }
        };

        t1.start();
        t2.start();
        t3.start();
        t1.join();
        t2.join();
        t3.join();
        System.out.println("Set size : "+value.get());
        System.out.println("main end !");

    }

}
