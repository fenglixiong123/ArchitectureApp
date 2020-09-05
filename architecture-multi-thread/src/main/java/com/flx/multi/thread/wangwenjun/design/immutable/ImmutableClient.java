package com.flx.multi.thread.wangwenjun.design.immutable;

import java.util.stream.IntStream;

/**
 * @Author Fenglixiong
 * @Create 2020/9/5 22:38
 * @Description
 **/
public class ImmutableClient {

    public static void main(String[] args) {

        Person p =  new Person("jack","shanghai");

        IntStream.rangeClosed(1,4).forEach(x->new PersonThread(p).start());

    }

}
