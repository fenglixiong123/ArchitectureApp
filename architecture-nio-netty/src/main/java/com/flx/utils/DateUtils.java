package com.flx.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Author: Fenglixiong
 * @Date: 2021/4/8 17:18
 * @Description:
 */
public class DateUtils {

    public static String now(){
        return now("yyyy-MM-dd HH:mm:ss");
    }

    public static String now(String patten){
        return toDateStr(new Date(),patten);
    }

    public static String toDateStr(Date source,String patten){
        SimpleDateFormat sdf = new SimpleDateFormat(patten);
        return sdf.format(source);
    }

}
