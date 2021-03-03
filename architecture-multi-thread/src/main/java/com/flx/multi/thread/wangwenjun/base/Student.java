package com.flx.multi.thread.wangwenjun.base;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: Fenglixiong
 * @Date: 2021/3/3 17:00
 * @Description:
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Student {

    private String name;
    private int age;

    public static Student of(String name,int age){
        return new Student(name,age);
    }

}
