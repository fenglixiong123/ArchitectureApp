package com.flx.multi.thread.wangwenjun.design.active.client;

import com.flx.multi.thread.wangwenjun.design.active.object.ActiveObject;

/**
 * @Author Fenglixiong
 * @Create 2020/9/13 11:29
 * @Description
 **/
public class DisplayClientThread extends Thread{

    private final ActiveObject activeObject;

    public DisplayClientThread(String name, ActiveObject activeObject) {
        super(name);
        this.activeObject = activeObject;
    }

    @Override
    public void run() {
        for (int i = 0; true ; i++) {
            String text = Thread.currentThread().getName()+"==>"+i;
            activeObject.displayString(text);
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
