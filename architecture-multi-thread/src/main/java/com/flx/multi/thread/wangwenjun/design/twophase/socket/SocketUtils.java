package com.flx.multi.thread.wangwenjun.design.twophase.socket;

import java.io.*;
import java.net.Socket;

/**
 * @Author Fenglixiong
 * @Create 2020/9/10 23:27
 * @Description
 **/
public class SocketUtils {

    public static void sendMessage(Socket socket,String message) throws IOException {
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        bw.write(message);
        bw.flush();
    }

    public static String readMessage(Socket socket) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        //注意细节 readLine()是阻塞式的，读取不到内容会一直阻塞，而且消息是以\n结束
        return br.readLine();
    }

}
