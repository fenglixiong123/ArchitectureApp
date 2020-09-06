package com.flx.multi.thread.wangwenjun.design.suspension;

/**
 * @Author Fenglixiong
 * @Create 2020/9/6 13:48
 * @Description
 *
 * 案例：客户端不断发送请求，服务端一直处理请求
 * 如果客户端发送过快就需要一个队列保存请求
 * 服务端一直去队列拿请求来处理
 **/
public class SuspensionApp {

    public static void main(String[] args) throws InterruptedException {

        RequestQueue requestQueue = new RequestQueue();
        Thread clientThread = new ClientThread(requestQueue,"shop");
        ServerThread serverThread = new ServerThread(requestQueue);
        clientThread.start();
        serverThread.start();

        Thread.sleep(120_000);

        System.out.println("request finished !");
        serverThread.shutdown();


    }

}
