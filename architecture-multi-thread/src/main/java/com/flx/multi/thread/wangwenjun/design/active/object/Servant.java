package com.flx.multi.thread.wangwenjun.design.active.object;

import com.flx.multi.thread.wangwenjun.design.active.object.ActiveObject;
import com.flx.multi.thread.wangwenjun.design.active.result.RealResult;
import com.flx.multi.thread.wangwenjun.design.active.result.Result;

import java.util.Arrays;

/**
 * @Author Fenglixiong
 * @Create 2020/9/12 19:39
 * @Description
 * 实际执行人
 **/
public class Servant implements ActiveObject {

    @Override
    public Result<String> makeString(int count, char fillChar) {
        char[] buffer = new char[count];
        Arrays.fill(buffer,fillChar);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return new RealResult<>(new String(buffer));
    }

    @Override
    public void displayString(String text) {
        System.out.println("Display:"+text);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
