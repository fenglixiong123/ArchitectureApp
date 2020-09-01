package com.flx.multi.thread.wangwenjun.communicate;

import java.util.Optional;
import java.util.stream.IntStream;

/**
 * @Author Fenglixiong
 * @Create 2020/9/1 22:28
 * @Description
 * 1.所有对象都有一个wait set，用来存放调用了该对象wait方法之后进入block状态的线程
 * 2.线程被notify之后不一定会立即得到执行，必须抢到锁
 * 3.线程从wait set中被唤醒顺序不一定
 * 4.线程被唤醒后必须重新获取锁
 **/
public class WaitSet {

    private static final Object LOCK = new Object();

    public static void main(String[] args) throws InterruptedException {

        //创建20个线程并wait等待
        IntStream.rangeClosed(1,20).forEach(x->
            new Thread(String.valueOf(x)){
                @Override
                public void run() {
                    synchronized (LOCK){
                        try {
                            Optional.of(Thread.currentThread().getName()+" will come to wait set ...").ifPresent(System.out::println);
                            LOCK.wait();
                            Optional.of(Thread.currentThread().getName()+" will leave to wait set !!!").ifPresent(System.out::println);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                    }
                }
            }.start()
        );

        Thread.sleep(1000);

        //在main线程中依次唤醒20个线程
        IntStream.rangeClosed(1,20).forEach(x->{
            synchronized (LOCK){
                LOCK.notify();
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

    }

}
