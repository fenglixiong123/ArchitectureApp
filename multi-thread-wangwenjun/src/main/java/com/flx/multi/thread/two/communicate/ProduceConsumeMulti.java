package com.flx.multi.thread.two.communicate;

import java.util.stream.Stream;

/**
 * @Author Fenglixiong
 * @Create 2020/8/26 22:26
 * @Description 生产者消费者模式
 * 多生产者和多消费者的时候可以使用notifyAll()
 * 唤醒所有线程去抢占资源
 **/
public class ProduceConsumeMulti {

    public static void main(String[] args) {

        ProduceConsumeMulti produceConsume = new ProduceConsumeMulti();

        Stream.of("P1","P2").forEach(x->{
            new Thread(()->{
                while (true){
                    produceConsume.produce();
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            },x).start();
        });

        Stream.of("C1","C2").forEach(x-> {
            new Thread(() -> {
                while (true) {
                    produceConsume.consume();
                }
            }, x).start();
        });
    }

    private int count = 0;
    private final Object lock = new Object();
    private volatile boolean isProduced = false;

    private void produce(){
        synchronized (lock){
            if(isProduced){
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }else {
                count++;
                System.out.println(Thread.currentThread().getName()+"-->" + count);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                isProduced = true;
                lock.notifyAll();
            }

        }
    }

    private void consume(){
        synchronized (lock){
            if(isProduced){
                System.out.println(Thread.currentThread().getName()+"-->" + count);
                isProduced = false;
                lock.notifyAll();
            }else {
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
