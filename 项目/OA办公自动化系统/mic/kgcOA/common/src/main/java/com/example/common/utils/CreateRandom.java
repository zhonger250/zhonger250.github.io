package com.example.common.utils;



import java.text.SimpleDateFormat;
/**
 * @Author: zhonger250
 * 生成唯一的数
 */
public class CreateRandom {
    public static String createRandom(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSSS");
        String markNo = sdf.format(System.currentTimeMillis());
        int x = (int) (Math.random()*900)+100;
        return markNo+x;
    }
}
