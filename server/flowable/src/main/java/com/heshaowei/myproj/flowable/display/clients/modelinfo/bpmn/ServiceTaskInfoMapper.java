package com.heshaowei.myproj.flowable.display.clients.modelinfo.bpmn;

import com.heshaowei.myproj.flowable.display.clients.modelinfo.AbstractInfoMapper;
import org.flowable.bpmn.model.ImplementationType;
import org.flowable.bpmn.model.ServiceTask;

public class ServiceTaskInfoMapper extends AbstractInfoMapper {
    public ServiceTaskInfoMapper() {
    }

    protected void mapProperties(Object element) {
        ServiceTask serviceTask = (ServiceTask)element;
        if (ImplementationType.IMPLEMENTATION_TYPE_CLASS.equals(serviceTask.getImplementationType())) {
            this.createPropertyNode("Class", serviceTask.getImplementation());
        } else if (ImplementationType.IMPLEMENTATION_TYPE_EXPRESSION.equals(serviceTask.getImplementationType())) {
            this.createPropertyNode("Expression", serviceTask.getImplementation());
        } else if (ImplementationType.IMPLEMENTATION_TYPE_DELEGATEEXPRESSION.equals(serviceTask.getImplementationType())) {
            this.createPropertyNode("Delegate expression", serviceTask.getImplementation());
        }

        this.createPropertyNode("Result variable name", serviceTask.getResultVariableName());
        this.createPropertyNode("Use local scope for result variable", serviceTask.isUseLocalScopeForResultVariable());
    }
}
