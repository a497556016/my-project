package com.heshaowei.myproj.flowable.display.clients.modelinfo.bpmn;

import com.heshaowei.myproj.flowable.display.clients.modelinfo.AbstractInfoMapper;
import org.flowable.bpmn.model.UserTask;

public class UserTaskInfoMapper extends AbstractInfoMapper {
    public UserTaskInfoMapper() {
    }

    protected void mapProperties(Object element) {
        UserTask userTask = (UserTask)element;
        this.createPropertyNode("Assignee", userTask.getAssignee());
        this.createPropertyNode("Candidate users", userTask.getCandidateUsers());
        this.createPropertyNode("Candidate groups", userTask.getCandidateGroups());
        this.createPropertyNode("Due date", userTask.getDueDate());
        this.createPropertyNode("Form key", userTask.getFormKey());
        this.createPropertyNode("Priority", userTask.getPriority());
    }
}
