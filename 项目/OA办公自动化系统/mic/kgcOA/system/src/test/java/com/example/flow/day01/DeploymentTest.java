package com.example.flow.day01;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: zhonger250
 * @Date: 2024/4/10 16:34
 * @Project: mic
 * @Description: 流程部署
 */
@SpringBootTest
public class DeploymentTest {

    @Resource
    private RepositoryService repositoryService;


    @Test
    public void initDeployment(){

        System.out.println(repositoryService);
        Deployment deploy = repositoryService.createDeployment()
                .addClasspathResource("bpmn/demo2.bpmn20.xml").deploy();
        System.out.println("流程部署ID"+deploy.getId());
    }



    public void deploymentQuery(){
        // 查询流程部署
        List<Deployment> list = repositoryService.createDeploymentQuery().list();
        for (Deployment deployment : list) {
            System.out.println(deployment.getId()+","+deployment.getName());
        }
    }


    public void deploymentDel(){
        repositoryService.deleteDeployment(repositoryService.createDeployment()
                .addClasspathResource("bpmn/demo2.bpmn20.xml").deploy().getId());
    }
}
