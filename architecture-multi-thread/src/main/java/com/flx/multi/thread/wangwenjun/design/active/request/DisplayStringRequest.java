package com.flx.multi.thread.wangwenjun.design.active.request;


import com.flx.multi.thread.wangwenjun.design.active.object.Servant;

/**
 * @Author Fenglixiong
 * @Create 2020/9/12 20:10
 * @Description
 **/
public class DisplayStringRequest extends MethodRequest<String>{

    private final String text;

    public DisplayStringRequest(Servant servant, final String text) {
        super(servant, null);
        this.text = text;
    }

    @Override
    public void execute() {
        servant.displayString(this.text);
    }

}
