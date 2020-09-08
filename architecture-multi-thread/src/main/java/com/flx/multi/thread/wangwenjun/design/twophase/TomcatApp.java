package com.flx.multi.thread.wangwenjun.design.twophase;

import java.io.*;
import java.net.Socket;

/**
 * @Author Fenglixiong
 * @Create 2020/9/8 23:23
 * @Description
 **/
public class TomcatApp {

    private final static String ip = "127.0.0.1";
    private final static int port = 16779;

    public static void main(String[] args) {

        AppServer appServer = new AppServer();
        appServer.start();

        AppClient appClient = new AppClient(ip,port);
        appClient.start();

    }

}
