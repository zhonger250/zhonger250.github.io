package com.example.workflow.form;

import lombok.Data;

/**
 * @Author: zhonger250
 * @Date: 2024/4/17 14:18
 * @Project: mic
 * @Description: 审批任务参数类
 */
@Data
public class ApprovalTaskForm {
    /**
     * 任务Id
     */
    private String taskId;
    /**
     * 审批结果
     */
    private String approval;
}
