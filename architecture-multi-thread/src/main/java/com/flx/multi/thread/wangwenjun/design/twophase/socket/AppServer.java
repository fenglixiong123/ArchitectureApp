package com.flx.multi.thread.wangwenjun.design.twophase.socket;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Author Fenglixiong
 * @Create 2020/9/8 22:52
 * @Description
 * 技巧应用案例
 **/
public class AppServer extends Thread{

    private final int port;

    private volatile boolean start = true;

    private List<AppServerThread> serverThreads = new ArrayList<>();

    private final ExecutorService executorService = Executors.newFixedThreadPool(10);

    public AppServer(String name,int port) {
        super(name);
        this.port = port;
        this.start();
    }

    @Override
    public void run() {
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            System.out.println("ServerSocket started at "+serverSocket.getLocalPort());
            while (start){
                Socket socketClient = serverSocket.accept();
                System.out.println(Thread.currentThread().getName()+" : a client linked "+socketClient.getInetAddress().getHostAddress()+":"+socketClient.getLocalPort());
                AppServerThread serverThread = new AppServerThread(socketClient);
                serverThreads.add(serverThread);
                executorService.execute(serverThread);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }finally {
            serverThreads.forEach(AppServerThread::stop);
            executorService.shutdown();
        }
    }

    public void shutdown(){
        this.start = false;
        this.interrupt();
    }

}
