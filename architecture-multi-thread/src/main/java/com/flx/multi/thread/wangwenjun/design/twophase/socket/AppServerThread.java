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
        try {
//            System.out.println(Thread.currentThread().getName()+" : Start a serverThread to deal client socket : "+socket.getInetAddress().getHostAddress()+":"+socket.getLocalPort());
//            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
//            bw.write(Thread.currentThread().getName()+socket.getRemoteSocketAddress()+" : Connection successful !");
//            bw.flush();
            new SendMessageThread(socket,"ServerSendThread").start();
            new ReceiveMessageThread(socket,"ServerReceiveThread").start();
//        } catch (IOException e) {
//            this.running = false;
        }finally {
//            this.stop();
        }
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
