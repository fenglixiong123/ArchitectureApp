package com.flx.multi.thread.wangwenjun.design.twophase;

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

    private int port;
    private final static int DEFAULT_PORT = 16779;
    private volatile boolean start = true;

    private List<AppServerThread> serverThreads = new ArrayList<>();

    private final ExecutorService executorService = Executors.newFixedThreadPool(10);

    public AppServer(){
        this(DEFAULT_PORT);
    }

    public AppServer(int port) {
        this.port = port;
    }

    @Override
    public void run() {
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            System.out.println("ServerSocket started at "+serverSocket.getLocalPort());
            while (start){
                Socket socketClient = serverSocket.accept();
                System.out.println("AppClient linked "+socketClient.getInetAddress().getHostAddress()+":"+socketClient.getLocalPort());
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
