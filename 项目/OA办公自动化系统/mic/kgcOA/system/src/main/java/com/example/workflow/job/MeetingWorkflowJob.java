package com.example.workflow.job;

import com.example.service.TbMeetingService;
import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.HistoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.runtime.ProcessInstance;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.concurrent.locks.ReentrantReadWriteLock;

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

    @Resource
    private HistoryService historyService;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        // 获得定时任务种传入的参数
        JobDataMap jobDataMap = jobExecutionContext.getJobDetail().getJobDataMap();
        // 会议ID
        String uuid = jobDataMap.get("uuid").toString();
        // 流程实例ID
        String processId = jobDataMap.get("processId").toString();
        // 根据流程实例, 获得流程实例
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(processId).singleResult();
        if (processInstance!=null){
            // 会议开始, 审批流程还未开始, 会议的申请状态改为审核未通过, 删除流程实例以及流程实例的历史信息
            jobDataMap.put("processStatus","流程未结束");
            runtimeService.deleteProcessInstance(processId,"会议已经开始, 流程实例未执行完毕");
            // 删除流程实例的历史记录
            long count = historyService.createHistoricTaskInstanceQuery().processInstanceId(processId).count();
            if (count>0){
                // 如果有历史记录, 就删除
                historyService.deleteHistoricProcessInstance(processId);
            }
            // 更改会议的状态
            tbMeetingService.updateMeetingStatus(uuid,2);
        }


//        // 获取任务中的参数
//        JobDataMap jobDataMap = jobExecutionContext.getJobDetail().getJobDataMap();
//        // 会议的唯一标识
//        String uuid = jobDataMap.get("uuid").toString();
//        // 流程实例Id
//        String instanceId = jobDataMap.get("instanceId").toString();
//        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processDefinitionId(instanceId).singleResult();
//
//        if (processInstance!=null){
//            // 流程实例不为空, 说明流程还未执行完毕
//            jobDataMap.put("processStatus","未结束");
//            // 删除流程, 删除定时器
//            runtimeService.deleteProcessInstance(instanceId,"会议已经开始, 审批已经过期");
//            // 获得此流程实例的历史记录, 如果历史记录数>0, 删除历史记录
//            long count = historyService.createHistoricProcessInstanceQuery().processInstanceId(instanceId).count();
//            if (count>0){
//                historyService.deleteHistoricProcessInstance(instanceId);
//            }
//            // 更改会议的状态, 将此会议的状态改为审批未通过
//            tbMeetingService.updateMeetingStatus(uuid,2);
//        }
    }
}
