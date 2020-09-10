package com.flx.multi.thread.wangwenjun.design.workthread;

import java.util.Arrays;
import java.util.stream.IntStream;

/**
 * @Author Fenglixiong
 * @Create 2020/9/10 23:43
 * @Description
 **/
public class Channel {

    private final static int max_request = 100;

    //自定义队列
    private final Work[] workQueue;

    private int head;
    private int tail;
    private int count;

    private final WorkThread[] workPools;

    public Channel(int works) {
        this.workQueue = new Work[max_request];
        this.head = 0;
        this.tail = 0;
        this.count = 0;
        this.workPools = new WorkThread[works];
        this.init();
    }

    private void init() {
        IntStream.rangeClosed(0,workPools.length-1)
                .forEach(i->workPools[i] = new WorkThread("Worker-"+i,this));
    }

    public void startWorker(){
        Arrays.asList(workPools).forEach(WorkThread::start);
    }

    public synchronized void put(Work request){
        while (count>=workQueue.length){
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        this.workQueue[tail] = request;
        this.tail = (tail+1)%workQueue.length;
        this.count++;
        this.notifyAll();
    }

    public synchronized Work take() {
        while (count <= 0) {
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        Work request = this.workQueue[head];
        this.head = (this.head + 1) % workQueue.length;
        this.count--;
        this.notifyAll();
        return request;
    }

}
