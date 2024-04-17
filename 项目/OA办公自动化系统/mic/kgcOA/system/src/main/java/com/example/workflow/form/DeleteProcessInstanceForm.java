package com.example.workflow.form;

import lombok.Data;

/**
 * @Author: zhonger250
 * @Date: 2024/4/17 16:33
 * @Project: mic
 * @Description: 删除的流程实例
 */
@Data
public class DeleteProcessInstanceForm {
    /**
     * 会议唯一标识
     */
    private String uuid;
    /**
     * 流程实例ID
     */
    private String instance;
    /**
     * 删除流程实例的类型
     */
    private String type;
    /**
     * 删除流程实例原因
     */
    private String reason;
}
