package com.example.workflow.job;

import com.example.service.TbMeetingService;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @Author: zhonger250
 * @Date: 2024/4/17 15:25
 * @Project: mic
 * @Description: 会议开始的定时任务
 */
@Component
@Slf4j
public class MeetingStartJob extends QuartzJobBean {

    @Resource
    private TbMeetingService tbMeetingService;
    
    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        // 获得会议的uuid
        JobDataMap jobDataMap = context.getJobDetail().getJobDataMap();
        String uuid = jobDataMap.get("uuid").toString();
        // 更改会议的状态
        tbMeetingService.updateMeetingStatus(uuid,4);
    }
}
