package com.flx.multi.thread.wangwenjun.design.twophase.socket;

import java.io.*;
import java.net.Socket;

/**
 * @Author Fenglixiong
 * @Create 2020/9/8 23:01
 * @Description
 **/
public class AppServerThread implements Runnable{

    private final Socket socket;

    private volatile boolean running = true;

    public AppServerThread(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {

        Thread s = new SendMessageThread(socket,"ServerSendThread");
        Thread r = new ReceiveMessageThread(socket,"ServerReceiveThread");
        //设置为守护线程，等此线程结束则守护线程一起结束
        s.setDaemon(true);
        r.setDaemon(true);
        s.start();
        r.start();
        while (running){
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
                break;
            }
        }
        System.out.println(Thread.currentThread().getName()+" will exit!");
    }

    public void stop(){
        if(!running){
            return;
        }
        this.running = false;
        try {
            this.socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
