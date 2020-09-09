package com.flx.multi.thread.wangwenjun.design.twophase.socket;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;

/**
 * @Author Fenglixiong
 * @Create 2020/9/9 22:50
 * @Description
 **/
public class SendMessageThread extends Thread{

    private final Socket socket;
    private BufferedWriter bw;

    public SendMessageThread(Socket socket,String name) {
        super(name);
        this.socket = socket;
        try {
            bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            while (true){
                synchronized (socket) {
                    String message = Thread.currentThread().getName() + " Hello!";
                    System.out.println(message);
                    bw.write(message);
//                    bw.flush();
                    Thread.sleep(3000);
                }
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

}
