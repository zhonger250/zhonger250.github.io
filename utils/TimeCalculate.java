package com.example.common.utils;

import cn.hutool.core.collection.CollUtil;
import com.example.common.constant.ResultConstant;
import com.example.common.exception.HttpException;
import com.example.entity.Shifts;

import java.util.List;

/**
 * @Author: zhonger250
 * @Date: 2024/3/6 13:17
 * @Description: 判断是否可以进行排班<p/>
 * 传入的是1.班次列表 2.开始时间 3.结束时间 3.是否跨自然日<p/>
 * @Version: 1.0
 */
public class TimeCalculate {

    /**
     * 时间池
     */
    private Boolean[] time;
    /**
     * 班次列表
     */
    private List<Shifts> list;
    /**
     * 开始时间
     */
    private String startTime;
    /**
     * 结束时间
     */
    private String endTime;
    /**
     * 是否跨自然日
     */
    private int isCross;

    public TimeCalculate() {
        this.time = new Boolean[1440];
    }

    public TimeCalculate(Boolean[] time, List<Shifts> list) {
        this.time = time;
        this.list = list;
    }

    public TimeCalculate(List<Shifts> list, String startTime, String endTime, int isCross) {
        this.time = new Boolean[1440];
        this.list = list;
        this.startTime = startTime;
        this.endTime = endTime;
        this.isCross = isCross;
    }

    public boolean calculate() {
        if (CollUtil.isNotEmpty(list)) {
            for (Shifts shift : list) {
                // 获得数据库时间
                String s2 = shift.getStartTime();
                // 结束时间
                String e2 = shift.getEndTime();
                // 只有时和分
                String[] st = s2.split(":");
                String[] et = e2.split(":");
                int sh = Integer.parseInt(st[0]);
                int sm = Integer.parseInt(st[1]);
                int eh = Integer.parseInt(et[0]);
                int em = Integer.parseInt(et[1]);
                if (shift.getIsCross() != 0) {
                    for (int i = sh * 60 + sm; i < 1440; i++) {
                        time[i] = true;
                    }
                    for (int i = 0; i < eh * 60 + em; i++) {
                        time[i] = true;
                    }
                } else {
                    for (int i = sh * 60 + sm; i < eh * 60 + em; i++) {
                        time[i] = true;
                    }
                }
            }
        }
        // 获得数据库时间
        String s2 = startTime;
        // 结束时间
        String e2 = endTime;
        // 只有时和分
        String[] st = s2.split(":");
        String[] et = e2.split(":");
        int sh = Integer.parseInt(st[0]);
        int sm = Integer.parseInt(st[1]);
        int eh = Integer.parseInt(et[0]);
        int em = Integer.parseInt(et[1]);

        if (sh * 60 + sm > eh * 60 + em) {
            throw new HttpException(ResultConstant.START_TIME_AFTER_END_TIME);
        }
        if (isCross != 0) {
            for (int i = sh * 60 + sm; i < 1440; i++) {
                if (time[i] != null) {
                    throw new HttpException(ResultConstant.TIME_HAVE_INTERSECTION);
                }
            }
            for (int i = 0; i < eh * 60 + em; i++) {
                if (time[i] != null) {
                    throw new HttpException(ResultConstant.TIME_HAVE_INTERSECTION);
                }
            }
        } else {
            for (int i = sh * 60 + sm; i < eh * 60 + em; i++) {
                if (time[i] != null) {
                    throw new HttpException(ResultConstant.TIME_HAVE_INTERSECTION);
                }
            }
        }
        return true;
    }

    /**
     * 获取
     *
     * @return time
     */
    public Boolean[] getTime() {
        return time;
    }

    /**
     * 设置
     *
     * @param time
     */
    public void setTime(Boolean[] time) {
        this.time = time;
    }

    /**
     * 获取
     *
     * @return list
     */
    public List<Shifts> getList() {
        return list;
    }

    /**
     * 设置
     *
     * @param list
     */
    public void setList(List<Shifts> list) {
        this.list = list;
    }

    /**
     * 获取
     *
     * @return startTime
     */
    public String getStartTime() {
        return startTime;
    }

    /**
     * 设置
     *
     * @param startTime
     */
    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    /**
     * 获取
     *
     * @return endTime
     */
    public String getEndTime() {
        return endTime;
    }

    /**
     * 设置
     *
     * @param endTime
     */
    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    /**
     * 获取
     *
     * @return isCross
     */
    public int getIsCross() {
        return isCross;
    }

    /**
     * 设置
     *
     * @param isCross
     */
    public void setIsCross(int isCross) {
        this.isCross = isCross;
    }

}
