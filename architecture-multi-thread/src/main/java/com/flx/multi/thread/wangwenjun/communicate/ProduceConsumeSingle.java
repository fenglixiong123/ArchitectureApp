package com.flx.multi.thread.wangwenjun.communicate;

/**
 * @Author Fenglixiong
 * @Create 2020/8/26 22:26
 * @Description 生产者消费者模式
 **/
public class ProduceConsumeSingle {

    public static void main(String[] args) {

        ProduceConsumeSingle produceConsume = new ProduceConsumeSingle();

        new Thread(()->{
            while (true){
                produceConsume.produce();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"T1").start();

        new Thread(()->{
            while (true){
                produceConsume.consume();
            }
        },"T2").start();

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
                System.out.println("Produce->" + count);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                isProduced = true;
                lock.notify();
            }

        }
    }

    private void consume(){
        synchronized (lock){
            if(isProduced){
                System.out.println("Consumer->" + count);
                isProduced = false;
                lock.notify();
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
