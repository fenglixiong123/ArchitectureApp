package com.flx.multi.thread.wangwenjun.design.twophase.socket;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @Author Fenglixiong
 * @Create 2020/9/8 23:23
 * @Description
 **/
public class TomcatApp {

    private final static String ip = "127.0.0.1";
    private final static int port = 8888;

    public static void main(String[] args) {

//        new AppServer("Appserver",port);

//        new AppClient("AppClient",ip,port);
//        new AppClient(ip,port);
//        new AppClient(ip,port);

        new Thread(()->{
            try {
                ServerSocket serverSocket = new ServerSocket(8888);
                while (true){
                    Socket client = serverSocket.accept();
                    new ReadThread(client).start();
                    new WriteThread(client).start();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();

        new Thread(()->{
            try {
                Socket client = new Socket("localhost", 8888);
                new ReadThread(client).start();
                new WriteThread(client).start();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();

    }

}

class ReadThread extends Thread{
    private Socket client;

    public ReadThread(Socket client) {
        this.client = client;
    }

    @Override
    public void run() {
        DataInputStream dis = null;
        try{
            while(true){
                //读取客户端数据
                dis = new DataInputStream(client.getInputStream());
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
                if(client != null){
                    client = null;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

class WriteThread extends Thread{

    private Socket client;

    public WriteThread(Socket client) {
        this.client = client;
    }

    @Override
    public void run() {
        DataOutputStream dos = null;
        BufferedReader br = null;
        try {
            while (true) {
                //向客户端回复信息
                dos = new DataOutputStream(client.getOutputStream());
                System.out.print("请输入:\t");
                // 键盘录入
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
                if (client != null) {
                    client = null;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
