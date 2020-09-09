package com.flx.multi.thread.wangwenjun.design.twophase.socket;

/**
 * @Author Fenglixiong
 * @Create 2020/9/10 1:23
 * @Description
 **/
public class TomcatClient {

    private final static String ip = "127.0.0.1";
    private final static int port = 8888;

    public static void main(String[] args) {

        new AppClient("AppClient",ip,port);

    }

}
