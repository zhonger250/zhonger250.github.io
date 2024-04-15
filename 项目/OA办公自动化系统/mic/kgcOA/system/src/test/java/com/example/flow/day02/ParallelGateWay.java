package com.example.flow.day02;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

/**
 * @Author: zhonger250
 * @Date: 2024/4/11 17:55
 * @Project: mic
 * @Description: 并行网关册数
 */
@SpringBootTest
public class ParallelGateWay {

    @Resource
    private RepositoryService repositoryService;
    @Resource
    private RuntimeService runtimeService;
    @Resource
    private TaskService taskService;
    ProcessInstance processInstance;
    @Test
    public void deployment(){
        repositoryService.createDeployment().addClasspathResource("bpmn/demo6.bpmn20.xml").deploy();
    }

    @Test
    public void start(){
        processInstance = runtimeService.startProcessInstanceByKey("demo6");
    }

    @Test
    public void aaaTaskComplete(){
        String aaa = taskService.createTaskQuery().taskAssignee("aaa").singleResult().getId();
        taskService.complete(aaa);
        System.out.println(processInstance.isEnded());
    }

    @Test
    public void bbbTaskComplete(){
        String bbb = taskService.createTaskQuery().taskAssignee("bbb").singleResult().getId();
        taskService.complete(bbb);
        System.out.println(processInstance.isEnded());
    }

    @Test
    public void status(){
        System.out.println(processInstance.isEnded());
    }
}
