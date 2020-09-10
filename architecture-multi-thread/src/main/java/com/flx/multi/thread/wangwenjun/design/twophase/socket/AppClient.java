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
        Thread s = new SendMessageThread(socket,"ClientSendThread");
        Thread r = new ReceiveMessageThread(socket,"ClientReceiveThread");
        s.setDaemon(true);
        r.setDaemon(true);
        s.start();
        r.start();
        while (true){
            try{
                //不断发送心跳检测信息如果对方没有响应则自动退出
                SocketUtils.sendMessage(socket,"heartbeats");
            }catch(Exception ex){
                break;
            }
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println(Thread.currentThread().getName()+" will exit!");
    }


}
