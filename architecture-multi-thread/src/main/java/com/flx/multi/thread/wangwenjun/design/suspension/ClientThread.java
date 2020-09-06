package com.flx.multi.thread.wangwenjun.design.suspension;

import java.util.Random;

/**
 * @Author Fenglixiong
 * @Create 2020/9/6 13:32
 * @Description
 **/
public class ClientThread extends Thread{

    private final RequestQueue requestQueue;

    private final Random random;

    private final String name;

    public ClientThread(RequestQueue requestQueue,String name) {
        this.requestQueue = requestQueue;
        this.name = name;
        this.random = new Random(System.currentTimeMillis());
    }

    @Override
    public void run() {
        for (int i = 1; i <= 1000; i++) {
            System.out.println("Client->"+name+i);
            requestQueue.putRequest(new Request(name+i));
            try {
                Thread.sleep(random.nextInt(100));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
