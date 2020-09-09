package com.flx.multi.thread.wangwenjun.design.twophase.socket;

import java.io.*;
import java.net.Socket;

/**
 * @Author Fenglixiong
 * @Create 2020/9/9 22:50
 * @Description
 **/
public class SendMessageThread extends Thread{

    private final Socket socket;

    public SendMessageThread(Socket socket,String name) {
        super(name);
        this.socket = socket;
    }

    @Override
    public void run() {
        BufferedReader br;
        BufferedWriter bw;
        try {
            while (true){
                br = new BufferedReader(new InputStreamReader(System.in));
                bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                String message = br.readLine();
                //注意细节 readLine()是阻塞式的，读取不到内容会一直阻塞，而且消息是以\n结束
                bw.write(Thread.currentThread().getName() +"->"+ message + "\n");
                bw.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
