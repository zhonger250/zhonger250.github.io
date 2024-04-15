package com.example.flow.day02;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: zhonger250
 * @Date: 2024/4/11 14:08
 * @Project: mic
 * @Description: 流程部署的单元测试
 */
@SpringBootTest
public class DeploymentTest {

    @Resource
    private RepositoryService repositoryService;

    @Test
    public void initDeployment(){
        Deployment deploy = repositoryService.createDeployment().addClasspathResource("bpmn/demo3.bpmn20.xml")
                .name("部署请假流程1").deploy();
        System.out.println("流程部署ID:"+deploy.getId());
    }


    @Test
    public void showDeploymentQuery(){
        List<Deployment> list = repositoryService.createDeploymentQuery().list();
        for (Deployment deployment : list) {
            System.out.println("部署流程ID:"+deployment.getId()+" 部署流程的名字:"+deployment.getName());
        }
    }


    @Test
    public void deploymentDelete(){
        repositoryService.deleteDeployment("f7cdaaba-f727-11ee-9d00-b445069b0fbc");
    }
}
