package com.flx.multi.thread.wangwenjun.design.active.object;

import com.flx.multi.thread.wangwenjun.design.active.ScheduleThread;
import com.flx.multi.thread.wangwenjun.design.active.request.DisplayStringRequest;
import com.flx.multi.thread.wangwenjun.design.active.request.MakeStringRequest;
import com.flx.multi.thread.wangwenjun.design.active.result.FutureResult;
import com.flx.multi.thread.wangwenjun.design.active.result.Result;

/**
 * @Author Fenglixiong
 * @Create 2020/9/13 11:00
 * @Description
 **/
class ActiveObjectProxy implements ActiveObject {

    private final ScheduleThread<String> scheduleThread;

    private final Servant servant;

    public ActiveObjectProxy(ScheduleThread<String> scheduleThread, Servant servant) {
        this.scheduleThread = scheduleThread;
        this.servant = servant;
    }

    @Override
    public Result<String> makeString(int count, char fillChar) {
        FutureResult<String> futureResult = new FutureResult<>();
        scheduleThread.invoke(new MakeStringRequest(servant,futureResult,count,fillChar));
        return futureResult;
    }

    @Override
    public void displayString(String text) {
        scheduleThread.invoke(new DisplayStringRequest(servant,text));
    }
}
