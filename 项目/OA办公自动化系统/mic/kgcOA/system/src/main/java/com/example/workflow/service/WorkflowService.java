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
}
