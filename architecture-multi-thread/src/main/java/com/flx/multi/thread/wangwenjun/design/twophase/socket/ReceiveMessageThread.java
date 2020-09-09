package com.flx.multi.thread.wangwenjun.design.twophase.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * @Author Fenglixiong
 * @Create 2020/9/9 22:51
 * @Description
 **/
public class ReceiveMessageThread extends Thread{

    private final Socket socket;

    public ReceiveMessageThread(Socket socket,String name) {
        super(name);
        this.socket = socket;
    }

    @Override
    public void run() {
        BufferedReader br;
        try {
            while (true){
                br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String message = br.readLine();
                if (message == null) {
                    continue;
                }
                System.out.println(Thread.currentThread().getName() + " receive message : " + message);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
