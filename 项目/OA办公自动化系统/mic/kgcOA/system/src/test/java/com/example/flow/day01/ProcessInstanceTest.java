package com.example.flow.day01;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.runtime.ProcessInstance;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

/**
 * @Author: zhonger250
 * @Date: 2024/4/10 17:57
 * @Project: mic
 * @Description: 一个流程定义可以给给多个实例
 */
@SpringBootTest
public class ProcessInstanceTest {

    @Resource
    private RuntimeService runtimeService;

    /**
     * 启动流程实例
     */
    @Test
    public void initProcessInstance(){
        String businessKey = "";
        ProcessInstance demo2 = runtimeService.startProcessInstanceByKey("demo2", businessKey);
        System.out.println(demo2.getProcessDefinitionId());
        System.out.println(demo2.isEnded());
    }

}
