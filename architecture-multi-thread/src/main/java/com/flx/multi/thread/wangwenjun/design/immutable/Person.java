package com.flx.multi.thread.wangwenjun.design.immutable;

import lombok.Getter;

/**
 * @Author Fenglixiong
 * @Create 2020/9/5 22:34
 * @Description 不可变对象是不会产生线程安全的
 **/
@Getter
final public class Person {

    private final String name;
    private final String address;

    public Person(String name, String address) {
        this.name = name;
        this.address = address;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
