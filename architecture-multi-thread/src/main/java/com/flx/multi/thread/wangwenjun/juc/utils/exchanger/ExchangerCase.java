package com.flx.multi.thread.wangwenjun.juc.utils.exchanger;

import java.util.concurrent.Exchanger;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author: Fenglixiong
 * @Date: 2021/3/14 23:12
 * @Description: exchange 可以持续交换
 */
public class ExchangerCase {

    public static void main(String[] args) {

        final Exchanger<Integer> exchanger = new Exchanger<>();

        new Thread(()->{
            try {
                AtomicInteger reference = new AtomicInteger(1);
                while (true) {
                    Thread.sleep(1000);
                    Integer result = exchanger.exchange(reference.addAndGet(1));
                    System.out.println(Thread.currentThread().getName() + " get exchange result " + result);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        new Thread(()->{
            try {
                AtomicInteger reference = new AtomicInteger(1);
                while (true) {
                    Thread.sleep(1000);
                    Integer result = exchanger.exchange(reference.addAndGet(2));
                    System.out.println(Thread.currentThread().getName() + " get exchange result " + result);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

    }
}
