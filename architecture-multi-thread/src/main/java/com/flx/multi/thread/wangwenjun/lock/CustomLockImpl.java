package com.flx.multi.thread.wangwenjun.lock;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 * @Author Fenglixiong
 * @Create 2020/8/29 17:03
 * @Description 自定义锁实现
 **/
public class CustomLockImpl implements CustomLock{

    //true:锁被占用
    //false:锁空闲
    private boolean locked;
    private Thread currentThread;
    private Collection<Thread> blockedThreadCollection = new ArrayList<>();

    @Override
    public synchronized void lock() throws InterruptedException {
        while (locked){
            blockedThreadCollection.add(Thread.currentThread());
            this.wait();
        }
        blockedThreadCollection.remove(Thread.currentThread());
        locked = true;
        currentThread = Thread.currentThread();
    }

    //带超时功能的锁
    @Override
    public synchronized void lock(long timeout) throws InterruptedException, TimeOutException {
        if(timeout<=0){
            lock();
        }
        long endTime = System.currentTimeMillis() + timeout;
        while (locked){
            if(System.currentTimeMillis() >= endTime){
                blockedThreadCollection.remove(Thread.currentThread());
                throw new TimeOutException("Time out");
            }
            blockedThreadCollection.add(Thread.currentThread());
            this.wait(timeout);
        }
        blockedThreadCollection.remove(Thread.currentThread());
        locked = true;
        currentThread = Thread.currentThread();
    }

    @Override
    public synchronized void unlock() {
        //只能自己释放锁
        if(Thread.currentThread() == currentThread) {
            locked = false;
            System.out.println(Thread.currentThread().getName() + " release lock");
            this.notify();
        }
    }

    @Override
    public Collection<Thread> getBlockedThread() {
        //返回一个不能修改只读的集合
        return Collections.unmodifiableCollection(blockedThreadCollection);
    }

    @Override
    public int getBlockedSize() {
        return blockedThreadCollection.size();
    }
}
