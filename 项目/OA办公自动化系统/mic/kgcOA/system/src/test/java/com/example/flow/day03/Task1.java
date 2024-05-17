package com.example.flow.day03;

import cn.hutool.core.lang.UUID;
import org.activiti.engine.HistoryService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: zhonger250
 * @Date: 2024/4/20 14:07
 * @Project: mic
 * @Description: 并行网关与流程变量
 */
@SpringBootTest
public class Task1 {
    /**
     * 部署流程文件, 查询流程定义的业务层
     */
    @Resource
    private RepositoryService repositoryService;

    /**
     * 启动流程实例的业务层
     */
    @Resource
    private RuntimeService runtimeService;

    /**
     * 任务相关
     */
    @Resource
    private TaskService taskService;

    /**
     * 历史任务相关
     */
    @Resource
    private HistoryService historyService;

    /**
     * 1.部署流程实例
     * 还有一种方案: 将流程文件放入到processes文件中, 项目启动时会自动部署流程文件
     */
    @Test
    public void test1(){
        // 部署流程实例
        repositoryService.createDeployment().addClasspathResource("bpmn/demo8.bpmn20.xml").deploy();
    }

    // 2.启动流程实例, 向流程实例种添加局部变量
    @Test
    public void test2(){
        Map<String,Object> map= new HashMap<>();
        map.put("uuid", UUID.fastUUID().toString());
        // 启动流程实例, 向流程实例中添加局部变量, 并获得流程实例
        String instanceId = runtimeService.startProcessInstanceByKey("demo8", map).getProcessInstanceId();
        System.out.println("流程实例ID:"+ instanceId);
    }

    // aaa用户查询自己等待处理的任务, 从流程实例种获得局部变量, 从aaa种获得任务的局部变量
    // aaa 0c22b3ba-a751-4a52-8370-0dd2c4708f9a   4ff672c2-fede-11ee-a6b5-b445069b0fbc
    // bbb 0c22b3ba-a751-4a52-8370-0dd2c4708f9a   4ff6e7f5-fede-11ee-a6b5-b445069b0fbc
    @Test
    public void test3(){
        Task task = taskService.createTaskQuery().includeProcessVariables().includeTaskLocalVariables().taskAssignee("aaa").singleResult();
        System.out.println("aaa获得的任务:"+task.getId());
        // 流程实例的变量
        Map<String, Object> processVariables = task.getProcessVariables();
        System.out.println("流程实例的变量:"+processVariables.get("uuid"));
        // 任务的局部变量
        Map<String, Object> taskLocalVariables = task.getTaskLocalVariables();
        System.out.println("流程实例的局部变量:"+taskLocalVariables.get("uuid"));
    }

    // bbb用户查询自己待处理的任务

    // aaa完成任务
    @Test
    public void test5(){
        // aaa完成自己的任务
        System.out.println("aaa完成任务");
        Map<String,Object> map = new HashMap<>();
        map.put("uuid","aaa修改了uuid的值");
        /// 流程实例种添加了Key叫做uuid, 值是aaa修改了uuid的值流程比那辆
        taskService.complete("4ff672c2-fede-11ee-a6b5-b445069b0fbc",map);

        // bbb完成自己的任务
        System.out.println("bbb完成任务");
        Object value = taskService.getVariable("4ff6e7f5-fede-11ee-a6b5-b445069b0fbc", "uuid");
        System.out.println(value);
        taskService.complete("4ff6e7f5-fede-11ee-a6b5-b445069b0fbc");
    }
}
