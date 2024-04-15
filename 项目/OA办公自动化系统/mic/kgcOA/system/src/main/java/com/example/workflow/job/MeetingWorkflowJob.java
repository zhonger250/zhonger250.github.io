package com.example.workflow.job;

import com.example.service.TbMeetingService;
import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.runtime.ProcessInstance;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @Author: zhonger250
 * @Date: 2024/4/15 15:16
 * @Project: mic
 * @Description: 会议定时器
 * <br>
 * 会议已经启动, 但是流程实例还未完成, 此时申请会议的流程实例是一个拒绝状态
 */
@Slf4j
@Component
public class MeetingWorkflowJob extends QuartzJobBean {
    @Resource
    private TbMeetingService tbMeetingService;

    @Resource
    private RuntimeService runtimeService;

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        // 获取任务中的参数
        JobDataMap jobDataMap = jobExecutionContext.getJobDetail().getJobDataMap();
        // 会议的唯一标识
        String uuid = jobDataMap.get("uuid").toString();
        // 流程实例Id
        String instanceId = jobDataMap.get("instanceId").toString();
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processDefinitionId(instanceId).singleResult();

        if (processInstance!=null){
            // 流程实例不为空, 说明流程还未执行完毕
            jobDataMap.put("processStatus","未结束");
            // todo 删除流程, 删除定时器
            // 更改会议的状态, 将此会议的状态改为审批未通过
            tbMeetingService.updateMeetingStatus(uuid,2);
        }
    }


}
