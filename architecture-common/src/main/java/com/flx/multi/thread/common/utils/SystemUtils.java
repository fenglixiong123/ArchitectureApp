package com.flx.multi.thread.common.utils;

/**
 * @Author: Fenglixiong
 * @Date: 2021/4/7 0:13
 * @Description:
 */
public class SystemUtils {

    public static void main(String[] args) {
        System.out.println("CPU = "+getCpuNumber());
    }

    public static int getCpuNumber(){
        return Runtime.getRuntime().availableProcessors();
    }

}
