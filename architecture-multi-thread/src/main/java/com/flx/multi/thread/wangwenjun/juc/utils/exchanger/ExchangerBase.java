package com.flx.multi.thread.wangwenjun.juc.utils.exchanger;

import java.util.concurrent.Exchanger;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * Exchange 线程之间互相交换数据
 * 1.必须在同一时间进行交换，如果伙伴没有交换，自己就一直阻塞等待伙伴交换数据
 * 2.注意必须成对出现，否则会出错
 */
public class ExchangerBase {

    public static void main(String[] args) {

        Exchanger<String> exchanger = new Exchanger<>();

        new Thread(()->{
            System.out.println(Thread.currentThread().getName()+" start ");
            try {
                Thread.sleep(2000);
                String result = exchanger.exchange("I com from T-A",5, TimeUnit.SECONDS);
                System.out.println(Thread.currentThread().getName()+" get exchange result "+result);
            } catch (InterruptedException | TimeoutException e) {
                e.printStackTrace();
                System.out.println(Thread.currentThread().getName()+" time out ");
            }
            System.out.println(Thread.currentThread().getName()+" done ! ");
        },"#A#").start();

        new Thread(()->{
            System.out.println(Thread.currentThread().getName()+" start ");
            try {
                Thread.sleep(4000);
                String result = exchanger.exchange("I com from T-B",5, TimeUnit.SECONDS);
                System.out.println(Thread.currentThread().getName()+" get exchange result "+result);
            } catch (InterruptedException | TimeoutException e) {
                e.printStackTrace();
                System.out.println(Thread.currentThread().getName()+" time out ");
            }
            System.out.println(Thread.currentThread().getName()+" done ! ");
        },"#B#").start();

        new Thread(()->{
            System.out.println(Thread.currentThread().getName()+" start ");
            try {
                Thread.sleep(8000);
                String result = exchanger.exchange("I com from T-C",10,TimeUnit.SECONDS);
                System.out.println(Thread.currentThread().getName()+" get exchange result "+result);
            } catch (InterruptedException | TimeoutException e) {
                e.printStackTrace();
                System.out.println(Thread.currentThread().getName()+" time out ");
            }
            System.out.println(Thread.currentThread().getName()+" done ! ");
        },"#C#").start();

    }

}
