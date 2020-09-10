package com.flx.multi.thread.wangwenjun.design.twophase.socket;

/**
 * @Author Fenglixiong
 * @Create 2020/9/8 23:23
 * @Description
 **/
public class TomcatServer {
    private final static int port = 8888;

    public static void main(String[] args) {

        AppServer s = new AppServer("Appserver",port);

        try {
            Thread.sleep(30000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        s.shutdown();

    }


}

