package com.flx.multi.thread.wangwenjun.design.consumer;

/**
 * @Author Fenglixiong
 * @Create 2020/9/8 0:12
 * @Description
 **/
public class ConsumerThread extends Thread{

    private final MessageQueue messageQueue;

    public ConsumerThread(String name, MessageQueue messageQueue) {
        super(name);
        this.messageQueue = messageQueue;
    }

    @Override
    public void run() {
        while (true) {
            MessageQueue.Message message = messageQueue.take();
            System.out.println(Thread.currentThread().getName()+" consume "+message);
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
