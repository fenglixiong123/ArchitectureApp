package com.flx.multi.thread.wangwenjun.design.suspension;

import java.util.Random;

/**
 * @Author Fenglixiong
 * @Create 2020/9/6 13:38
 * @Description
 **/
public class ServerThread extends Thread{

    private final RequestQueue requestQueue;

    private final Random random;

    private volatile boolean stop = false;

    public ServerThread(RequestQueue requestQueue) {
        this.requestQueue = requestQueue;
        random = new Random(System.currentTimeMillis());
    }

    @Override
    public void run() {
        while (!stop){
            Request request = requestQueue.getRequest();
            if(null==request){
                System.out.println("Receive null request !");
                continue;
            }
            System.out.println("Server->"+request.getValue());
            try {
                Thread.sleep(random.nextInt(1000));
            } catch (InterruptedException e) {
                return;
            }
        }
    }

    public void shutdown(){
        this.stop = true;
        this.interrupt();
    }

}
