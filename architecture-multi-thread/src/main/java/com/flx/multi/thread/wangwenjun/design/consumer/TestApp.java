package com.flx.multi.thread.wangwenjun.design.consumer;

/**
 * @Author Fenglixiong
 * @Create 2020/9/8 0:13
 * @Description
 **/
public class TestApp {

    public static void main(String[] args) {

        MessageQueue messageQueue = new MessageQueue(10);

        ProduceThread produceThread1 = new ProduceThread("P1-Thread",messageQueue);
        ProduceThread produceThread2 = new ProduceThread("P2-Thread",messageQueue);
        ProduceThread produceThread3 = new ProduceThread("P3-Thread",messageQueue);
        ConsumerThread consumerThread = new ConsumerThread("C1-Thread",messageQueue);

        produceThread1.start();
        produceThread2.start();
        produceThread3.start();
        consumerThread.start();

    }

}
