package com.example.flow.day02;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.ProcessDefinition;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: zhonger250
 * @Date: 2024/4/11 14:26
 * @Project: mic
 * @Description: 流程定义测试
 */
@SpringBootTest
public class ProcessDefinitionTest {
    @Resource
    private RepositoryService repositoryService;


    @Test
    public void processDefinitionQuery(){
        List<ProcessDefinition> list = repositoryService.createProcessDefinitionQuery().orderByProcessDefinitionId().asc().list();
        for (ProcessDefinition processDefinition : list) {
            System.out.println("流程定义的key:  "+processDefinition.getKey());
            System.out.println("流程定义的id:  "+processDefinition.getId());
            System.out.println("流程定义的名称:  "+processDefinition.getName());
            System.out.println("流程定义的部署ID:  "+processDefinition.getDeploymentId());
            System.out.println("流程定义的资源名称:  "+processDefinition.getResourceName());
        }
    }

}
