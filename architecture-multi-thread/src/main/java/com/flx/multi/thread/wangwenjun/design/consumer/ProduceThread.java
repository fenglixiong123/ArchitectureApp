package com.flx.multi.thread.wangwenjun.design.consumer;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author Fenglixiong
 * @Create 2020/9/8 0:10
 * @Description
 **/
public class ProduceThread extends Thread{

    private final MessageQueue messageQueue;

    private static AtomicInteger count = new AtomicInteger(1);

    public ProduceThread(String name, MessageQueue messageQueue) {
        super(name);
        this.messageQueue = messageQueue;
    }

    @Override
    public void run() {
        for (int i = 1; i <= 100; i++) {
            messageQueue.put(new MessageQueue.Message("M-"+(count.getAndAdd(1))));
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
