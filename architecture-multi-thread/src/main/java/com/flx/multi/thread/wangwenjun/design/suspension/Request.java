package com.flx.multi.thread.wangwenjun.design.suspension;

import lombok.Getter;

/**
 * @Author Fenglixiong
 * @Create 2020/9/6 13:23
 * @Description
 **/
@Getter
public class Request {

    final private String value;

    public Request(String value) {
        this.value = value;
    }
}
