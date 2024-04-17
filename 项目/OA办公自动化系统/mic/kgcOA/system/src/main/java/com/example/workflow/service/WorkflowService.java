package com.example.workflow.service;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: zhonger250
 * @Date: 2024/4/15 15:48
 * @Project: mic
 * @Description: 工作流的接口
 */
public interface WorkflowService {

    /**
     * 启动流程实例
     * @param param
     * @return
     */
    String startProcessInstance(Map param);

    /**
     * 查询任务
     * @param param
     * @return
     */
    HashMap searchTaskByPage(Map param);

    /**
     * 查询会议审批的详细信息(数据库中的会议信息 时长 审批的结果)
     * @param instanceId 流程实例ID
     * @param userId 用户ID
     * @param role 角色
     * @param type 任务类型
     * @param status 任务状态
     * @return
     */
    HashMap searchApprovalContent(String instanceId,String userId,String [] role,String type,String status);

    /**
     * 审批任务
     * @param param
     */
    void approvalTask(HashMap param);

    /**
     * 删除流程实例的业务操作
     * @param uuid
     * @param instanceId
     * @param type
     * @param reason
     */
    void deleteProcessInstance(String uuid,String instanceId,String type,String reason);



}
