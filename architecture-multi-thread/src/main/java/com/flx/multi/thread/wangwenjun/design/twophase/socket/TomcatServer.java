package com.flx.multi.thread.wangwenjun.design.twophase.socket;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @Author Fenglixiong
 * @Create 2020/9/8 23:23
 * @Description
 **/
public class TomcatServer {
    private final static int port = 8888;

    public static void main(String[] args) {

        new AppServer("Appserver",port);

    }


}

