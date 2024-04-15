package com.example.flow.day02;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.runtime.ProcessInstance;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

/**
 * @Author: zhonger250
 * @Date: 2024/4/11 14:35
 * @Project: mic
 * @Description: 流程实例测试
 */
@SpringBootTest
public class ProcessInstanceTest {

    @Resource
    private RuntimeService runtimeService;

    /**
     * 启动流程实例: 程序中运行了一个流程(启动请假流程).
     */
    @Test
    public void startProcessInstance(){
        // 业务表中的主键(保存一些请假的业务信息: 请假类型, 请假的时间, 请假的理由)
        String businessKey = "";
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("demo3", businessKey);
        System.out.println("流程实例的ID:"+processInstance.getId());
        System.out.println("流程实例的名称:"+processInstance.getName());
        System.out.println("流程实例是否结束:"+processInstance.isEnded());
//        System.out.println("流程实例的ID:"+processInstance.getId());
//        System.out.println("流程实例的ID:"+processInstance.getId());
    }


    @Test
    public void suspendActiveProcessInstance(){
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("demo3", "");

        if (processInstance.isSuspended()){
            // 如果流程实例挂起, 激活流程实例
            System.out.println("流程实例挂起, 就激活");
            runtimeService.activateProcessInstanceById(processInstance.getId());
        }else {
            // 如果流程实例是激活的, 挂起流程实例
            System.out.println("流程实例激活, 就挂起");
            runtimeService.suspendProcessInstanceById(processInstance.getId());
        }

        // 删除流程实例
        System.out.println("删除");
        runtimeService.deleteProcessInstance(processInstance.getId(), "没有原因");
    }
}
