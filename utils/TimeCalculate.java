package com.example.common.utils;

import com.example.common.constant.ResultConstant;
import com.example.common.exception.HttpException;
import com.example.entity.Shifts;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: zhonger250
 * @Date: 2024/3/6 13:17
 * @Description: 判断是否可以进行排班<p />
 * 传入的是1.班次列表 2.开始时间 3.结束时间 3.是否跨自然日<p/>
 * 注意:<p/>
 * 1.确保数据库内的数据是正确的<p/
 * 2.数据精确到分钟<p/
 * 3.数据不能有重复<p/
 * @Version: 1.0
 */
public class TimeCalculate {
    /**
     * 数据库内的信息
     */
    private static HashMap<String, HashMap<String, Boolean[]>> allTimes;

    /**
     * 一小时有60分钟
     */
    private static final Integer MINUTE = 60;

    /**
     * 一天有1440分钟
     */
    private static final Integer MINUTE_OF_DAY = 1440;
    private static int sh;
    private static int sm;
    private static int eh;
    private static int em;

    /**
     * 所有工厂, 所有时令的时间
     * 第一个String是工厂
     * 第二个String是时令
     * List是班次
     */

    public static void calculate(String factoryModelId, String seasonId, String startTime, String endTime, Integer isCross, HashMap<String, HashMap<String, Boolean[]>> allTimes) {

        Boolean[] time = allTimes.get(factoryModelId).get(seasonId);
        
        set(startTime,endTime);

        // 开始时间大于结束时间

        if (isCross != 0) {
            // 开始时间大于结束时间
            if (sh * MINUTE + sm < eh * MINUTE + em) {
                throw new HttpException(ResultConstant.START_TIME_AFTER_END_TIME);
            }
            for (int i = sh * MINUTE + sm; i < MINUTE_OF_DAY; i++) {
                if (Boolean.TRUE.equals(time[i])) {
                    throw new HttpException(ResultConstant.TIME_HAVE_INTERSECTION);
                }
            }
            for (int i = 0; i < eh * MINUTE + em; i++) {
                if (Boolean.TRUE.equals(time[i])) {
                    throw new HttpException(ResultConstant.TIME_HAVE_INTERSECTION);
                }
            }
        } else {
            // 开始时间大于结束时间
            if (sh * MINUTE + sm > eh * MINUTE + em) {
                throw new HttpException(ResultConstant.START_TIME_AFTER_END_TIME);
            }
            for (int i = sh * MINUTE + sm; i < eh * MINUTE + em; i++) {
                if (Boolean.TRUE.equals(time[i])) {
                    throw new HttpException(ResultConstant.TIME_HAVE_INTERSECTION);
                }
            }
        }
    }

    /**
     * 赋值
     */
    public static Boolean[] setBoolean(String startTime, String endTime, Integer isCross, boolean b, Boolean[] time) {

        set(startTime,endTime);

        if (isCross != 0) {
            for (int i = sh * MINUTE + sm; i < MINUTE_OF_DAY; i++) {
                time[i] = b;
            }
            for (int i = 0; i < eh * MINUTE + em; i++) {
                time[i] = b;
            }
        } else {
            for (int i = sh * MINUTE + sm; i < eh * MINUTE + em; i++) {
                time[i] = b;
            }
        }
        return time;
    }

    private static void set(String s1,String s2){

        // 只有时和分
        String[] st = s1.split(":");
        String[] et = s2.split(":");
        sh = Integer.parseInt(st[0]);
        sm = Integer.parseInt(st[1]);
        eh = Integer.parseInt(et[0]);
        em = Integer.parseInt(et[1]);
    }


    public static HashMap<String, HashMap<String, Boolean[]>> getAllTime(List<Shifts> shifts){
        // 没数据
        if (shifts.size()==0){
            return null;
        }
        // 得到所有工厂模型的ID
        List<String> factoryModelIds = shifts.stream().map(Shifts::getFactoryModelId).distinct().collect(Collectors.toList());
        // 得到所有不同时令
        List<String> seasonIds = shifts.stream().map(Shifts::getSeasonId).distinct().collect(Collectors.toList());
        allTimes = new HashMap<>(5);
        // 遍历ID
        factoryModelIds.forEach(s -> {
            HashMap<String, Boolean[]> seasonMap = new HashMap<>(5);
            // 遍历列表
            // 判断列表是否相等
            seasonIds.forEach(season -> {
                Boolean[] b = new Boolean[1440];
                for (Shifts shift : shifts) {
                    if (shift.getSeasonId().equals(season)) {
                        TimeCalculate.setBoolean(shift.getStartTime(),shift.getEndTime(),shift.getIsCross(), true, b);
                    }
                }
                seasonMap.put(season, b);
            });
            allTimes.put(s, seasonMap);
        });
        return allTimes;
    }
}
