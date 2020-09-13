package com.flx.multi.thread.wangwenjun.design.active.object;

import com.flx.multi.thread.wangwenjun.design.active.result.Result;

/**
 * @Author Fenglixiong
 * @Create 2020/9/12 19:35
 * @Description
 * 接受异步消息的主动方法
 **/
public interface ActiveObject {

    /**
     * 创建字符串
     * @param count
     * @param fillChar
     * @return
     */
    Result<String> makeString(int count, char fillChar);

    /**
     * 展示字符串
     * @param text
     */
    void displayString(String text);

}
