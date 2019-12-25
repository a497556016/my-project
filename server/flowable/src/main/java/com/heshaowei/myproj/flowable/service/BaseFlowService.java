package com.heshaowei.myproj.flowable.service;

import com.google.common.collect.Maps;
import com.heshaowei.myproj.flowable.bean.TestProcessAssignUsers;
import org.flowable.engine.IdentityService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.engine.task.Comment;
import org.flowable.task.api.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
public class BaseFlowService {
    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private IdentityService identityService;

    @Transactional
    public ProcessInstance startProcess(String businessKey, String firstAssign) {
        identityService.setAuthenticatedUserId(firstAssign);

        Map<String, Object> variables = Maps.newHashMap();
        variables.put("assignUsers", new TestProcessAssignUsers("heshaowei", "admin"));
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("TestProcess", businessKey, variables);
        Task task = this.getCurTask(processInstance.getId());
        System.out.println("当前任务："+task);
        taskService.setAssignee(task.getId(), firstAssign);
        return processInstance;
    }

    public void completeTask(String taskId, String userId, String message) {
        completeTask(taskId, userId, null, message);
    }

    public void completeTask(String taskId, String userId) {
        completeTask(taskId, userId, null, null);
    }

    public void completeTask(String taskId, String userId, Map<String, Object> variables) {
        completeTask(taskId, userId, variables, null);
    }

    public void completeTask(String taskId, String userId, Map<String, Object> variables, String message) {
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();

        if(null != message) {
            taskService.addComment(taskId, task.getProcessInstanceId(), "suggestion", message);
        }
        if(null != variables) {
            taskService.complete(taskId, variables);
        }else{
            taskService.complete(taskId);
        }

        Task newTask = getCurTask(task.getProcessInstanceId());
        taskService.setAssignee(newTask.getId(), userId);
    }

    public Task getCurTask(String processInstanceId) {
        return taskService.createTaskQuery().processInstanceId(processInstanceId).active().singleResult();
    }

    @Transactional
    public List<Task> getTasks(String assignee) {
        return taskService.createTaskQuery().taskAssignee(assignee).list();
    }

    public void changeState(String instanceId, String activityId, String newActivityId) {
        runtimeService.createChangeActivityStateBuilder().processInstanceId(instanceId).moveActivityIdTo(activityId, newActivityId).changeState();
    }
}
