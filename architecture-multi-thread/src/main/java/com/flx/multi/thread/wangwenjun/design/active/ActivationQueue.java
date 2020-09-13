package com.flx.multi.thread.wangwenjun.design.active;

import com.flx.multi.thread.wangwenjun.design.active.request.MethodRequest;

import java.util.LinkedList;

/**
 * @Author Fenglixiong
 * @Create 2020/9/12 20:57
 * @Description
 **/
public class ActivationQueue<T> {

    private final static int max_size = 100;

    private final LinkedList<MethodRequest<T>> methodQueue;

    public ActivationQueue() {
        this.methodQueue = new LinkedList<>();
    }

    public synchronized void put(MethodRequest<T> request){
        while (methodQueue.size()>=max_size){
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        this.methodQueue.addLast(request);
        this.notifyAll();
    }

    public synchronized MethodRequest take(){
        while (methodQueue.size()<=0){
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        MethodRequest methodRequest = methodQueue.removeFirst();
        this.notifyAll();
        return methodRequest;
    }

}
