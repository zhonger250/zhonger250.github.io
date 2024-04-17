package com.example.workflow.service;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.map.MapUtil;
import com.example.common.utils.QuartzUtil;
import com.example.service.TbMeetingService;
import com.example.workflow.job.MeetingEndJob;
import com.example.workflow.job.MeetingStartJob;
import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: zhonger250
 * @Date: 2024/4/15 15:03
 * @Project: mic
 * @Description: 会议通知类
 * <br>
 * notifyMeetingService
 */
@Component
@Slf4j
public class NotifyMeetingService implements JavaDelegate {
    @Resource
    private QuartzUtil quartzUtil;
    @Resource
    private TbMeetingService tbMeetingService;


    @Override
    public void execute(DelegateExecution execution) {
        // 获得流程实例的所有变量
        Map<String, Object> variables = execution.getVariables();
        // 会议的唯一标识符
        String uuid = MapUtil.getStr(variables, "uuid");
        // 获得审批结果
        String result = MapUtil.getStr(variables, "result");


        // 1.如果任务能够执行到发送通知的节点上, 说明任务已经蛇皮完成了(无论是否通过) 此时检查任务开始的定时器就可以删除了
        quartzUtil.deleteJob(uuid,"会议通过流程");

        if (result.equals("同意")){
            // 2.判断审批结果同意, 如果审批通过, 发送通知.
            System.out.println("给参与会议的人员发送会议通知");
            // 将会议的状态改为审批通过
            tbMeetingService.updateMeetingStatus(uuid,3);
            // 获得会议信息
            HashMap hashMap = tbMeetingService.searchMeetingByUUID(uuid);
            String date = MapUtil.getStr(hashMap, "date");
            String start = MapUtil.getStr(hashMap, "start");
            String end = MapUtil.getStr(hashMap, "end");
            // 会议开始时间
            DateTime meetStartTime = DateUtil.parse(date + " " + start, "yyyy-MM-dd HH:mm");
            // 会议结束时间
            DateTime meetEndTime = DateUtil.parse(date + " " + end, "yyyy-MM-dd HH:mm");
            // 同时创建一个会议开始的定时器, 此定时器在会议开始执行时将会议的状态改为"进行中"
            JobDetail jobDetail = JobBuilder.newJob(MeetingStartJob.class).build();
            jobDetail.getJobDataMap().put("uuid",uuid);
            quartzUtil.addJob(jobDetail,uuid,"会议开始定时任务组",meetStartTime);
            // 创建一个会议结束的定时器, 此定时器会在会议结束时将会议的状态改为"已结束"
            JobDetail jobDetail2 = JobBuilder.newJob(MeetingEndJob.class).build();
            jobDetail.getJobDataMap().put("uuid",uuid);
            quartzUtil.addJob(jobDetail2,uuid,"会议结束定时任务组",meetEndTime);
        } else {
            // 3.如果审批不通过, 将会议的状态改为审批未通过
            tbMeetingService.updateMeetingStatus(uuid,2);
        }
    }
}
