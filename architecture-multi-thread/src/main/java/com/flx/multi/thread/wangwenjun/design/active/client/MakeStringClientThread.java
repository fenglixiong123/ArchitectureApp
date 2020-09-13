package com.flx.multi.thread.wangwenjun.design.active.client;

import com.flx.multi.thread.wangwenjun.design.active.object.ActiveObject;
import com.flx.multi.thread.wangwenjun.design.active.result.Result;

/**
 * @Author Fenglixiong
 * @Create 2020/9/13 11:38
 * @Description
 **/
public class MakeStringClientThread extends Thread{

    private final ActiveObject activeObject;
    private final char fillChar;

    public MakeStringClientThread(String name, ActiveObject activeObject) {
        super(name);
        this.activeObject = activeObject;
        this.fillChar = name.charAt(0);
    }

    @Override
    public void run() {
        for (int i = 0; true; i++) {
            Result<String> future = activeObject.makeString(i + 1, fillChar);
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            String result = future.getResult();
            System.out.println(Thread.currentThread().getName()+"===>"+result);
        }
    }
}
