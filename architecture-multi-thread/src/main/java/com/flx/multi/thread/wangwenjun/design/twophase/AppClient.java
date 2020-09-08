package com.flx.multi.thread.wangwenjun.design.twophase;

import java.io.*;
import java.net.Socket;

/**
 * @Author Fenglixiong
 * @Create 2020/9/8 23:45
 * @Description
 **/
public class AppClient extends Thread{

    private Socket socket;

    public AppClient(String ip,int port){
        try {
            socket = new Socket(ip,port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName()+" : Client connected to "+socket.getInetAddress().getHostAddress()+":"+socket.getLocalPort());
        new SendMessageThread(socket,"ClientSendThread").start();
        new ReceiveMessageThread(socket,"ClientReceiveThread").start();
    }

    static class SendMessageThread extends Thread{
        private String name;
        private final Socket socket;

        public SendMessageThread(Socket socket,String name) {
            super(name);
            this.socket = socket;
        }

        @Override
        public void run() {
            try {
                while (true){
                    BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                    String message = Thread.currentThread().getName()+" Hello!";
                    System.out.println(message);
                    bw.write(message);
                    bw.flush();
                    Thread.sleep(3000);
                }
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    static class ReceiveMessageThread extends Thread{
        private String name;
        private final Socket socket;

        public ReceiveMessageThread(Socket socket,String name) {
            super(name);
            this.socket = socket;
        }

        @Override
        public void run() {
            try {
                BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                while (true){
                    String message = br.readLine();
                    if(message==null){
                        continue;
                    }
                    System.out.println(Thread.currentThread().getName()+" receive message from server : "+message);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
