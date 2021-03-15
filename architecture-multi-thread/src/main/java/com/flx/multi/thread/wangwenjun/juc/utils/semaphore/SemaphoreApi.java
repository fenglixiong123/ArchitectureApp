package com.flx.multi.thread.wangwenjun.juc.utils.semaphore;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

/**
 * @Author: Fenglixiong
 * @Date: 2021/3/14 23:54
 * @Description: 信号量api
 * 当permit为1时，可以当lock使用
 * acquire() 申请许可证，申请不到就阻塞等待
 * release() 释放许可证
 */
public class SemaphoreApi {

    public static void main(String[] args) {

        //许可证为2
        final Semaphore semaphore = new Semaphore(2);

        IntStream.rangeClosed(1,3).forEach(x->{
            new Thread(()->{
                System.out.println(Thread.currentThread().getName()+" start");
                try {
                    //申请许可证
                    semaphore.acquire();
                    System.out.println(Thread.currentThread().getName()+" get semaphore permit");
                    TimeUnit.SECONDS.sleep(5);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally {
                    semaphore.release();
                }
                System.out.println(Thread.currentThread().getName()+" end");
            },"T"+x).start();
        });



    }

}
