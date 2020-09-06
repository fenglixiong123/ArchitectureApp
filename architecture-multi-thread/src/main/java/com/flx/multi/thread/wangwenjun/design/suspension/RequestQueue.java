package com.flx.multi.thread.wangwenjun.design.suspension;

import java.util.LinkedList;

/**
 * @Author Fenglixiong
 * @Create 2020/9/6 13:23
 * @Description
 * 请求队列
 **/
public class RequestQueue {

    private final LinkedList<Request>  queue = new LinkedList<>();

    public Request getRequest(){
        synchronized (queue){
            while (queue.size()<1){
                try {
                    queue.wait();
                } catch (InterruptedException e) {
                    return null;
                }
            }
            return queue.removeFirst();
        }
    }

    public void putRequest(Request request){
        synchronized (queue){
            if(queue.size()>50){
                System.out.println("queue max is 50 !");
                return;
            }
            queue.addLast(request);
            queue.notifyAll();
        }
    }

}
