package com.heshaowei.myproj.flowable;

import com.heshaowei.myproj.flowable.service.BaseFlowService;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.flowable.engine.IdentityService;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.engine.task.Comment;
import org.flowable.idm.api.Group;
import org.flowable.idm.api.User;
import org.flowable.task.api.Task;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = FlowableApplication.class)
public class TestProcessTest {

    @Autowired
    private BaseFlowService flowService;

    @Autowired
    private IdentityService identityService;


    @Test
    public void createUser() {
        User user = identityService.newUser("heshaowei");
        user.setDisplayName("何少伟");
        user.setFirstName("少伟");
        user.setLastName("何");
        user.setEmail("heshaowei_code@163.com");
        user.setPassword("123456");
        identityService.saveUser(user);
        identityService.createMembership(user.getId(), "IT");
    }

    @Test
    public void createGroup() {
        Group group = identityService.newGroup("IT");
        group.setName("IT信息部门");
        group.setType("org");
        identityService.saveGroup(group);
    }

    @Test
    public void removeUser(){
        identityService.deleteUser("heshaowei");
        identityService.deleteMembership("heshaowei", "IT");
    }

    @Test
    public void test(){
        String businessKey = DateFormatUtils.format(new Date(), "yyyyMMddHHmm");
        ProcessInstance pi = flowService.startProcess(businessKey, "heshaowei");
        System.out.println(pi);

        List<Task> tasks = flowService.getTasks("heshaowei");
        System.out.println(tasks);

        Task task = flowService.getCurTask(pi.getId());
        flowService.completeTask(task.getId(), "heshaowei", "agree!");

        task = flowService.getCurTask(pi.getId());
        System.out.println(task);
    }
}
