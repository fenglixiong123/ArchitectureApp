package com.flx.multi.thread.wangwenjun.design.active.request;

import com.flx.multi.thread.wangwenjun.design.active.object.Servant;
import com.flx.multi.thread.wangwenjun.design.active.result.FutureResult;
import com.flx.multi.thread.wangwenjun.design.active.result.Result;

/**
 * @Author Fenglixiong
 * @Create 2020/9/12 20:04
 * @Description
 **/
public class MakeStringRequest extends MethodRequest<String>{

    private final int count;
    private final char fillChar;

    public MakeStringRequest(Servant servant, FutureResult<String> futureResult, int count, char fillChar) {
        super(servant, futureResult);
        this.count = count;
        this.fillChar = fillChar;
    }

    @Override
    public void execute() {
        Result<String> result = servant.makeString(count, fillChar);
        this.futureResult.done(result);
    }

}
