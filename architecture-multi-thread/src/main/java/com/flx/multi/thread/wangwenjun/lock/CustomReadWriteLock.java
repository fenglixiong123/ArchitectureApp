package com.flx.multi.thread.wangwenjun.lock;


/**
 * @Author Fenglixiong
 * @Create 2020/9/5 21:34
 * @Description 读写锁
 *
 **/
public class CustomReadWriteLock {

    private int readingReaders = 0;
    private int waitingReaders = 0;
    private int writingWriters = 0;
    private int waitingWriters = 0;

    public synchronized void readLock() throws InterruptedException {
        this.waitingReaders++;
        try {
            while (writingWriters > 0) {
                this.wait();
            }
            this.readingReaders++;
        }finally {
            waitingReaders--;
        }
    }

    public synchronized void readUnlock(){
        this.readingReaders--;
        this.notifyAll();
    }

    public synchronized void writeLock() throws InterruptedException {
        this.writingWriters++;
        try{
            while (readingReaders>0||writingWriters>0){
                this.wait();
            }
            this.writingWriters++;
        }finally {
            this.writingWriters--;
        }
    }

    public synchronized void writeUnlock(){
        this.writingWriters--;
        this.notifyAll();
    }

}
