package com.flx.multi.thread.wangwenjun.design.twophase.socket;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @Author Fenglixiong
 * @Create 2020/9/10 1:20
 * @Description
 **/
public class SocketTest {

    public static void main(String[] args) {

        //Server
        new Thread(()->{
            try {
                ServerSocket serverSocket = new ServerSocket(8888);
                while (true){
                    Socket client = serverSocket.accept();
                    startReadThread(client);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();

        //Client
        new Thread(()->{
            try {
                Socket client = new Socket("localhost", 8888);
                startWriteThread(client);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();

    }

    private static void startReadThread(final Socket socket){
        new Thread(()->{
            DataInputStream dis = null;
            try{
                while(true){
                    //读取客户端数据
                    dis = new DataInputStream(socket.getInputStream());
                    String reciver = dis.readUTF();
                    System.out.println("客户端发过来的内容:" + reciver);
                }
            }catch(Exception e){
                e.printStackTrace();
            }finally{
                try {
                    if(dis != null){
                        dis.close();
                    }
                    if(socket != null){
                        socket.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private static void startWriteThread(final Socket socket){
        new Thread(()->{
            DataOutputStream dos = null;
            BufferedReader br = null;
            try {
                while (true) {
                    //取得输出流
                    dos = new DataOutputStream(socket.getOutputStream());
                    System.out.print("请输入: \t");
                    //键盘录入
                    br = new BufferedReader(new InputStreamReader(System.in));
                    String send = br.readLine();
                    //发送数据
                    dos.writeUTF(send);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (dos != null) {
                        dos.close();
                    }
                    if (br != null) {
                        br.close();
                    }
                    if (socket != null) {
                        socket.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

}
