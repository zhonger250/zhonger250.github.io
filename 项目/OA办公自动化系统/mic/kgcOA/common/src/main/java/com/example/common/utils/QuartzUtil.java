package com.example.common.utils;

import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @Author: zhonger250
 * @Date: 2024/4/15 14:16
 * @Project: mic
 * @Description: 定时任务工具类
 */
@Component
@Slf4j
public class QuartzUtil {
    @Resource
    private Scheduler scheduler;
    /**
     * 添加定时器
     * 给job添加一个定时任务
     * @param jobDetail 定时器任务对象
     * @param jobName 任务名字
     * @param jobGroupName 任务组名字
     * @param start 开始日期时间
     */
    public void addJob(JobDetail jobDetail, String jobName, String jobGroupName, Date start) {
        try {
            //触发条件
            Trigger trigger = TriggerBuilder.newTrigger()
                    //设置定时器的名字和所在分组。
                    .withIdentity(jobName, jobGroupName)
                    //定时器种类：普通定时器。
                    .withSchedule(
                            SimpleScheduleBuilder.simpleSchedule()
                                    //错过的定时器立即执行。
                                    .withMisfireHandlingInstructionFireNow()
                    ).startAt(start).build();
            //构建定时器
            scheduler.scheduleJob(jobDetail, trigger);
            log.debug("成功添加" + jobName + "定时器");
        } catch (SchedulerException e) {
            log.error("定时器添加失败", e);
        }
    }
    /**
     * 查询是否存在定时器
     * @param jobName 任务名字
     * @param jobGroupName 任务组名字
     * @return
     */
    public boolean checkExists(String jobName, String jobGroupName) {
        TriggerKey triggerKey = new TriggerKey(jobName, jobGroupName);
        try {
            boolean bool = scheduler.checkExists(triggerKey);
            return bool;
        } catch (Exception e) {
            log.error("定时器查询失败", e);
            return false;
        }
    }
    /**
     * 删除定时器
     * @param jobName 任务名字
     * @param jobGroupName 任务组名字
     */
    public void deleteJob(String jobName, String jobGroupName) {
        // 触发器
        TriggerKey triggerKey = TriggerKey.triggerKey(jobName, jobGroupName);
        try {
            //停下定时器
            scheduler.resumeTrigger(triggerKey);
            //解除定时器
            scheduler.unscheduleJob(triggerKey);
            //删除定时器任务
            scheduler.deleteJob(JobKey.jobKey(jobName, jobGroupName));
            log.debug("成功删除", jobName + "定时器");
        } catch (SchedulerException e) {
            log.error("定时器删除失败", e);
        }
    }
}

