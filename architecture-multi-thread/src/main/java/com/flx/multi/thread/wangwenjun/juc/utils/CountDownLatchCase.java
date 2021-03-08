package com.flx.multi.thread.wangwenjun.juc.utils;

import java.util.concurrent.CountDownLatch;

/**
 * @Author Fenglixiong
 * @Create 2021/3/8 22:07
 * @Description 两个线程T1，T2共同等待T3执行的结果然后再继续执行
 **/
public class CountDownLatchCase {

    private static String db_result = null;

    public static void main(String[] args) {

        final CountDownLatch latch = new CountDownLatch(1);

        //T1线程先执行，执行完就等T3线程的计算结果，等T3线程结果出来，T1线程继续执行
        Thread t1 = new Thread(() -> {
            System.out.println(Thread.currentThread().getName()+"Some initial work doing...");
            try {
                Thread.sleep(1000);
                latch.await();
                String result = getResultFromMysql();
                System.out.println(Thread.currentThread().getName()+"Success get result = ["+result+"],start to do other things...");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"T1");

        t1.start();

        //T2线程先执行，执行完就等T3线程的计算结果，等T3线程结果出来，T2线程继续执行
        Thread t2 = new Thread(() -> {
            System.out.println(Thread.currentThread().getName()+"Some initial work doing...");
            try {
                Thread.sleep(1000);
                latch.await();
                String result = getResultFromMysql();
                System.out.println(Thread.currentThread().getName()+"Success get result = ["+result+"],start to do other things...");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"T2");

        t2.start();

        //T3线程计算出结果，将结果存入数据库中
        Thread t3 = new Thread(()->{
            System.out.println(Thread.currentThread().getName()+" start to work...");
            try {
                Thread.sleep(2000);
                String result = "Fenglixiong";
                saveResultToMysql(result);
                System.out.println(Thread.currentThread().getName()+" work done .");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }finally {
                latch.countDown();
            }
        },"T3");

        t3.start();

    }

    //模拟数据库存数据操作
    private static void saveResultToMysql(String result) {
        System.out.println(Thread.currentThread().getName()+" setDbResult start...");
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        CountDownLatchCase.db_result = result;
        System.out.println(Thread.currentThread().getName()+" setDbResult done...");
    }

    //模拟数据库取数据操作
    private static String getResultFromMysql() {
        System.out.println(Thread.currentThread().getName()+" getDbResult start...");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName()+" getDbResult done...");
        return CountDownLatchCase.db_result;
    }


}
