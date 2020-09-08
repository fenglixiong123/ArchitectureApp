package com.flx.multi.thread.wangwenjun.design.perthread;

import lombok.Getter;

import java.util.Random;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Author Fenglixiong
 * @Create 2020/9/8 22:05
 * @Description
 **/
public class MessageHandler {

    private final static Random random = new Random(System.currentTimeMillis());

    private final static ExecutorService executorService = Executors.newFixedThreadPool(5);

    public void request(Message message){
        executorService.execute(()->{
            try {
                String value = message.getMessage();
                Thread.sleep(random.nextInt(1000));
                System.out.println("The message ["+value+"] will handle by "+Thread.currentThread().getName());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }

    public void shutdown(){
        executorService.shutdown();
    }

    @Getter
    static class Message{
        private final String message;

        public Message(String message) {
            this.message = message;
        }
    }

}
