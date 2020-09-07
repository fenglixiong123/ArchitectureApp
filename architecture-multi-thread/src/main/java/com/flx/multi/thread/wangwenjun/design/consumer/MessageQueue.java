package com.flx.multi.thread.wangwenjun.design.consumer;

import lombok.Data;

import java.util.LinkedList;

/**
 * @Author Fenglixiong
 * @Create 2020/9/7 23:51
 * @Description
 **/
public class MessageQueue {

    private final LinkedList<Message> queue;

    private final static int default_limit = 10;

    private int limit;

    public MessageQueue(int limit){
        this.queue = new LinkedList<>();
        this.limit = limit;
    }

    public MessageQueue(){
        this(default_limit);
    }

    public void put(Message message){
        synchronized (queue){
            while (queue.size()>=limit){
                try {
                    queue.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println(Thread.currentThread().getName()+" produce "+message);
            queue.addLast(message);
            queue.notifyAll();
        }
    }

    public Message take(){
        synchronized (queue){
            while (queue.size()==0){
                try {
                    queue.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            Message message = queue.removeFirst();
            queue.notifyAll();
            return message;
        }
    }

    @Data
    static class Message{
        private String data;
        public Message(String data) {
            this.data = data;
        }

        @Override
        public String toString() {
            return "Message{" +
                    "data='" + data + '\'' +
                    '}';
        }
    }

}
