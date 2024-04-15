package com.example.flow.day02;

import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: zhonger250
 * @Date: 2024/4/11 15:08
 * @Project: mic
 * @Description: 任务测试
 */
@SpringBootTest
public class TaskTest {

    @Resource
    private TaskService taskService;


    // 查询当前任务信息
    @Test
    public void findAllTask() {
        List<Task> list = taskService.createTaskQuery().orderByTaskCreateTime().desc().list();
        for (Task task : list) {
            System.out.println("任务ID:  " + task.getId());
            System.out.println("任务名称:  " + task.getName());
            System.out.println("任务待办人:  " + task.getAssignee());
            System.out.println("任务状态:  " + task.getDueDate());
        }
    }


    // 查询八戒的待办任务
    @Test
    public void findBaJieTask() {
        List<Task> list = taskService.createTaskQuery().taskAssignee("bajie").list();
        for (Task task : list) {
            System.out.println("任务ID:  " + task.getId());
            System.out.println("任务名称:  " + task.getName());
            System.out.println("任务待办人:  " + task.getAssignee());
            System.out.println("任务状态:  " + task.getDueDate());
            System.out.println(task.getFormKey());
        }
    }


    // 查询悟空的待办任务
    @Test
    public void findWuKongTask() {
        List<Task> list = taskService.createTaskQuery().taskAssignee("wukong").list();
        for (Task task : list) {
            System.out.println("任务ID:  " + task.getId());
            System.out.println("任务名称:  " + task.getName());
            System.out.println("任务待办人:  " + task.getAssignee());
            System.out.println("任务状态:  " + task.getDueDate());
            System.out.println(task.getFormKey());
        }
    }
    // 八戒去完成任务
    @Test
    public void bajieCompleteTask(){
        taskService.complete("02bc4d6b-f7cf-11ee-9c97-b445069b0fbc");
    }

    // 查询悟空的待办任务

    // 悟空去完成任务
    @Test
    public void wukongCompleteTask(){
        taskService.complete("08592a67-f7d5-11ee-b639-b445069b0fbc");
    }
}
