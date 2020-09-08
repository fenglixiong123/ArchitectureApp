package com.flx.multi.thread.wangwenjun.design.twophase;

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
            System.out.println(Thread.currentThread().getName()+" : Start a serverThread to deal client socket : "+socket.getInetAddress().getHostAddress()+":"+socket.getLocalPort());
            BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            bw.write(Thread.currentThread().getName()+socket.getRemoteSocketAddress()+" : Connection successful !");
            bw.flush();
            new SendMessageThread(socket,"ServerSendThread").start();
            new ReceiveMessageThread(socket,"ServerReceiveThread").start();
        } catch (IOException e) {
            this.running = false;
        }finally {
            this.stop();
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
                    System.out.println(Thread.currentThread().getName()+" receive message from server : "+message);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
