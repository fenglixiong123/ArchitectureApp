package com.flx.multi.thread.wangwenjun.design.active.request;

import com.flx.multi.thread.wangwenjun.design.active.object.Servant;
import com.flx.multi.thread.wangwenjun.design.active.result.FutureResult;

/**
 * @Author Fenglixiong
 * @Create 2020/9/12 19:38
 * @Description
 * 对应ActiveObject的每一个方法
 **/
public abstract class MethodRequest<T> {

    protected final Servant servant;

    protected final FutureResult<T> futureResult;

    public MethodRequest(Servant servant, FutureResult<T> futureResult) {
        this.servant = servant;
        this.futureResult = futureResult;
    }

    public abstract void execute();



}
