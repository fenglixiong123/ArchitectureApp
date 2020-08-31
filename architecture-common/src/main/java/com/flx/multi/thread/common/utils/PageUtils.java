package com.flx.multi.thread.common.utils;

/**
 * @Author Fenglixiong
 * @Create 2020/8/6 1:53
 * @Description
 **/
public class PageUtils {

    public static int getTotalPage(int totalRecord,int pageSize){
        return (totalRecord+pageSize-1)/pageSize;
    }

    public static int getTotalPageA(int totalRecord,int pageSize){
        return totalRecord % pageSize == 0 ? totalRecord/pageSize : totalRecord/pageSize+1;
    }

    public static int getTotalPageB(int totalRecord,int pageSize){
        return (int)Math.ceil(totalRecord/pageSize);
    }


}
