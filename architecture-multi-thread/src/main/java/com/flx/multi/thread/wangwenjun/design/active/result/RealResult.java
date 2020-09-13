package com.flx.multi.thread.wangwenjun.design.active.result;

/**
 * @Author Fenglixiong
 * @Create 2020/9/12 19:43
 * @Description
 **/
public class RealResult<T> implements Result<T> {

    private final T resultValue;

    public RealResult(T resultValue) {
        this.resultValue = resultValue;
    }

    @Override
    public T getResult() {
        return resultValue;
    }

}
