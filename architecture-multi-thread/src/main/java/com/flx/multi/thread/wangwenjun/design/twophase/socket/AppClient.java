package com.flx.multi.thread.wangwenjun.design.twophase.socket;

import java.io.*;
import java.net.Socket;

/**
 * @Author Fenglixiong
 * @Create 2020/9/8 23:45
 * @Description
 **/
public class AppClient extends Thread{

    private Socket socket;

    public AppClient(String name,String ip,int port){
        super(name);
        try {
            socket = new Socket(ip,port);
            this.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName()+" : i connected to "+socket.getInetAddress().getHostAddress()+":"+socket.getLocalPort());
        new SendMessageThread(socket,"ClientSendThread").start();
//        new ReceiveMessageThread(socket,"ClientReceiveThread").start();
    }


}
