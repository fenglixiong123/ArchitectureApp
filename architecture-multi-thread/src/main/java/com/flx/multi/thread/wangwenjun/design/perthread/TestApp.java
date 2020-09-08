package com.flx.multi.thread.wangwenjun.design.perthread;

import java.util.stream.IntStream;

/**
 * @Author Fenglixiong
 * @Create 2020/9/8 22:09
 * @Description
 **/
public class TestApp {

    public static void main(String[] args) throws InterruptedException {
        MessageHandler handler = new MessageHandler();
        System.out.println("Tomcat started...");
        IntStream.rangeClosed(1,9)
                .forEach(e->handler.request(new MessageHandler.Message(String.valueOf(e))));

        Thread.sleep(10000);
        handler.shutdown();
        System.out.println("Tomcat ended...");
    }

}
