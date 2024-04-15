package com.example.flow.day01;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.ProcessDefinition;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: zhonger250
 * @Date: 2024/4/10 17:52
 * @Project: mic
 * @Description:
 */

@SpringBootTest
public class DefinitionTest {

    @Resource
    private RepositoryService repositoryService;

    /**
     * 查询流程定义
     */
    @Test
    public void definitionQuery() {
        List<ProcessDefinition> processDefinitionList =
                repositoryService.createProcessDefinitionQuery().list();


        processDefinitionList.forEach(processDefinition -> {
            System.out.println("流程定义ID:" + processDefinition.getId());
            System.out.println("流程定义名称:" + processDefinition.getName());
            System.out.println("流程定义Key:" + processDefinition.getKey());
            System.out.println("流程定义版本:" + processDefinition.getVersion());
            System.out.println("流程定义部署ID:" + processDefinition.getDeploymentId());
        });
    }

    /**
     * 使用流程部署的ID,删除流程定义信息
     */
    @Test
    public void definitionDelete() {
        repositoryService
                .deleteDeployment("f63e1ad0-f79f-11ee-9672-b445069b0fbc");
    }
}
