package com.flx.multi.thread.wangwenjun.design.active;

import com.flx.multi.thread.wangwenjun.design.active.client.DisplayClientThread;
import com.flx.multi.thread.wangwenjun.design.active.client.MakeStringClientThread;
import com.flx.multi.thread.wangwenjun.design.active.object.ActiveObject;
import com.flx.multi.thread.wangwenjun.design.active.object.ActiveObjectFactory;

/**
 * @Author Fenglixiong
 * @Create 2020/9/12 19:23
 * @Description ActiveObject设计模式
 *
 **/
public class TestApp {

    public static void main(String[] args) {
        ActiveObject activeObject = ActiveObjectFactory.buildActiveObject();
        new MakeStringClientThread("MakeClient",activeObject).start();
        new MakeStringClientThread("JakeClient",activeObject).start();
        new MakeStringClientThread("WakeClient",activeObject).start();
        new DisplayClientThread("DisplayClient",activeObject).start();
    }

}
