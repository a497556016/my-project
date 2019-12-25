//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.heshaowei.myproj.flowable.display.clients;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.heshaowei.myproj.flowable.display.service.ProcessDefinitionService;
import com.heshaowei.myproj.flowable.display.service.ProcessInstanceService;
import org.apache.commons.lang3.StringUtils;
import org.flowable.bpmn.model.BpmnModel;
import org.flowable.bpmn.model.ConditionalEventDefinition;
import org.flowable.bpmn.model.ErrorEventDefinition;
import org.flowable.bpmn.model.EscalationEventDefinition;
import org.flowable.bpmn.model.Event;
import org.flowable.bpmn.model.EventDefinition;
import org.flowable.bpmn.model.FlowElement;
import org.flowable.bpmn.model.FlowNode;
import org.flowable.bpmn.model.GraphicInfo;
import org.flowable.bpmn.model.Lane;
import org.flowable.bpmn.model.MessageEventDefinition;
import org.flowable.bpmn.model.Pool;
import org.flowable.bpmn.model.Process;
import org.flowable.bpmn.model.SequenceFlow;
import org.flowable.bpmn.model.ServiceTask;
import org.flowable.bpmn.model.SignalEventDefinition;
import org.flowable.bpmn.model.SubProcess;
import org.flowable.bpmn.model.TimerEventDefinition;
import com.heshaowei.myproj.flowable.display.clients.modelinfo.InfoMapper;
import com.heshaowei.myproj.flowable.display.clients.modelinfo.bpmn.ServiceTaskInfoMapper;
import com.heshaowei.myproj.flowable.display.clients.modelinfo.bpmn.UserTaskInfoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"/app"})
public class DisplayJsonClientResource {
    private static final Logger LOGGER = LoggerFactory.getLogger(DisplayJsonClientResource.class);
    @Autowired
    protected ProcessDefinitionService clientService;
    @Autowired
    protected ProcessInstanceService processInstanceService;
    protected ObjectMapper objectMapper = new ObjectMapper();
    protected List<String> eventElementTypes = new ArrayList();
    protected Map<String, InfoMapper> propertyMappers = new HashMap();

    public DisplayJsonClientResource() {
        this.eventElementTypes.add("StartEvent");
        this.eventElementTypes.add("EndEvent");
        this.eventElementTypes.add("BoundaryEvent");
        this.eventElementTypes.add("IntermediateCatchEvent");
        this.eventElementTypes.add("ThrowEvent");
        this.propertyMappers.put("ServiceTask", new ServiceTaskInfoMapper());
        this.propertyMappers.put("UserTask", new UserTaskInfoMapper());
    }

    @RequestMapping(
        value = {"/rest/admin/process-definitions/{processDefinitionId}/model-json"},
        method = {RequestMethod.GET},
        produces = {"application/json"}
    )
    public JsonNode getProcessDefinitionModelJSON(@PathVariable String processDefinitionId) {
//        ServerConfig config = this.retrieveServerConfig(EndpointType.PROCESS);
        ObjectNode displayNode = this.objectMapper.createObjectNode();
        BpmnModel pojoModel = this.clientService.getProcessDefinitionModel(processDefinitionId);
        if (!pojoModel.getLocationMap().isEmpty()) {
            try {
                GraphicInfo diagramInfo = new GraphicInfo();
                this.processProcessElements(pojoModel, displayNode, diagramInfo, (Set)null, (Set)null);
                displayNode.put("diagramBeginX", diagramInfo.getX());
                displayNode.put("diagramBeginY", diagramInfo.getY());
                displayNode.put("diagramWidth", diagramInfo.getWidth());
                displayNode.put("diagramHeight", diagramInfo.getHeight());
            } catch (Exception var6) {
                LOGGER.error("Error creating model JSON", var6);
            }
        }

        return displayNode;
    }

    @RequestMapping(
        value = {"/rest/admin/process-instances/{processInstanceId}/model-json"},
        method = {RequestMethod.GET},
        produces = {"application/json"}
    )
    public JsonNode getProcessInstanceModelJSON(@PathVariable String processInstanceId, @RequestParam(required = true) String processDefinitionId) {
        ObjectNode displayNode = this.objectMapper.createObjectNode();
//        ServerConfig config = this.retrieveServerConfig(EndpointType.PROCESS);
        BpmnModel pojoModel = this.clientService.getProcessDefinitionModel(processDefinitionId);
        if (!pojoModel.getLocationMap().isEmpty()) {
            List<String> completedActivityInstances = this.processInstanceService.getCompletedActivityInstancesAndProcessDefinitionId(processInstanceId);
            List<String> currentActivityinstances = this.processInstanceService.getCurrentActivityInstances(processInstanceId);
            List completedFlows = this.gatherCompletedFlows(completedActivityInstances, currentActivityinstances, pojoModel);

            try {
                GraphicInfo diagramInfo = new GraphicInfo();
                Set<String> completedElements = new HashSet(completedActivityInstances);
                completedElements.addAll(completedFlows);
                Set<String> currentElements = new HashSet(currentActivityinstances);
                this.processProcessElements(pojoModel, displayNode, diagramInfo, completedElements, currentElements);
                displayNode.put("diagramBeginX", diagramInfo.getX());
                displayNode.put("diagramBeginY", diagramInfo.getY());
                displayNode.put("diagramWidth", diagramInfo.getWidth());
                displayNode.put("diagramHeight", diagramInfo.getHeight());
                ArrayNode completedSequenceFlows;
                Iterator var13;
                String current;
                if (completedActivityInstances != null) {
                    completedSequenceFlows = displayNode.putArray("completedActivities");
                    var13 = completedActivityInstances.iterator();

                    while(var13.hasNext()) {
                        current = (String)var13.next();
                        completedSequenceFlows.add(current);
                    }
                }

                if (currentActivityinstances != null) {
                    completedSequenceFlows = displayNode.putArray("currentActivities");
                    var13 = currentActivityinstances.iterator();

                    while(var13.hasNext()) {
                        current = (String)var13.next();
                        completedSequenceFlows.add(current);
                    }
                }

                if (completedFlows != null) {
                    completedSequenceFlows = displayNode.putArray("completedSequenceFlows");
                    var13 = completedFlows.iterator();

                    while(var13.hasNext()) {
                        current = (String)var13.next();
                        completedSequenceFlows.add(current);
                    }
                }
            } catch (Exception var15) {
                LOGGER.error("Error creating model JSON", var15);
            }
        }

        return displayNode;
    }

    @RequestMapping(
        value = {"/rest/admin/process-instances/{processInstanceId}/history-model-json"},
        method = {RequestMethod.GET},
        produces = {"application/json"}
    )
    public JsonNode getHistoryProcessInstanceModelJSON(@PathVariable String processInstanceId, @RequestParam(required = true) String processDefinitionId) {
        ObjectNode displayNode = this.objectMapper.createObjectNode();
//        ServerConfig config = this.retrieveServerConfig(EndpointType.PROCESS);
        BpmnModel pojoModel = this.clientService.getProcessDefinitionModel(processDefinitionId);
        if (!pojoModel.getLocationMap().isEmpty()) {
            List<String> completedActivityInstances = this.processInstanceService.getCompletedActivityInstancesAndProcessDefinitionId(processInstanceId);
            List completedFlows = this.gatherCompletedFlows(completedActivityInstances, (List)null, pojoModel);

            try {
                GraphicInfo diagramInfo = new GraphicInfo();
                Set<String> completedElements = new HashSet(completedActivityInstances);
                completedElements.addAll(completedFlows);
                this.processProcessElements(pojoModel, displayNode, diagramInfo, completedElements, (Set)null);
                displayNode.put("diagramBeginX", diagramInfo.getX());
                displayNode.put("diagramBeginY", diagramInfo.getY());
                displayNode.put("diagramWidth", diagramInfo.getWidth());
                displayNode.put("diagramHeight", diagramInfo.getHeight());
                ArrayNode completedSequenceFlows;
                Iterator var11;
                String current;
                if (completedActivityInstances != null) {
                    completedSequenceFlows = displayNode.putArray("completedActivities");
                    var11 = completedActivityInstances.iterator();

                    while(var11.hasNext()) {
                        current = (String)var11.next();
                        completedSequenceFlows.add(current);
                    }
                }

                if (completedFlows != null) {
                    completedSequenceFlows = displayNode.putArray("completedSequenceFlows");
                    var11 = completedFlows.iterator();

                    while(var11.hasNext()) {
                        current = (String)var11.next();
                        completedSequenceFlows.add(current);
                    }
                }
            } catch (Exception var13) {
                LOGGER.error("Error creating model JSON", var13);
            }
        }

        return displayNode;
    }

    protected List<String> gatherCompletedFlows(List<String> completedActivityInstances, List<String> currentActivityinstances, BpmnModel pojoModel) {
        List<String> completedFlows = new ArrayList();
        List<String> activities = new ArrayList(completedActivityInstances);
        if (currentActivityinstances != null) {
            activities.addAll(currentActivityinstances);
        }

        Iterator var6 = pojoModel.getMainProcess().getFlowElements().iterator();

        while(true) {
            FlowElement activity;
            int index;
            do {
                do {
                    do {
                        if (!var6.hasNext()) {
                            return completedFlows;
                        }

                        activity = (FlowElement)var6.next();
                    } while(!(activity instanceof FlowNode));

                    index = activities.indexOf(activity.getId());
                } while(index < 0);
            } while(index + 1 >= activities.size());

            List<SequenceFlow> outgoingFlows = ((FlowNode)activity).getOutgoingFlows();
            Iterator var10 = outgoingFlows.iterator();

            while(var10.hasNext()) {
                SequenceFlow flow = (SequenceFlow)var10.next();
                String destinationFlowId = flow.getTargetRef();
                if (destinationFlowId.equals(activities.get(index + 1))) {
                    completedFlows.add(flow.getId());
                }
            }
        }
    }

    protected void processProcessElements(BpmnModel pojoModel, ObjectNode displayNode, GraphicInfo diagramInfo, Set<String> completedElements, Set<String> currentElements) throws Exception {
        if (!pojoModel.getLocationMap().isEmpty()) {
            ArrayNode elementArray = this.objectMapper.createArrayNode();
            ArrayNode flowArray = this.objectMapper.createArrayNode();
            ArrayNode collapsedArray = this.objectMapper.createArrayNode();
            if (!CollectionUtils.isEmpty(pojoModel.getPools())) {
                ArrayNode poolArray = this.objectMapper.createArrayNode();
                boolean firstElement = true;

                for(Iterator var12 = pojoModel.getPools().iterator(); var12.hasNext(); firstElement = false) {
                    Pool pool = (Pool)var12.next();
                    ObjectNode poolNode = this.objectMapper.createObjectNode();
                    poolNode.put("id", pool.getId());
                    poolNode.put("name", pool.getName());
                    GraphicInfo poolInfo = pojoModel.getGraphicInfo(pool.getId());
                    this.fillGraphicInfo(poolNode, poolInfo, true);
                    Process process = pojoModel.getProcess(pool.getId());
                    if (process != null && !CollectionUtils.isEmpty(process.getLanes())) {
                        ArrayNode laneArray = this.objectMapper.createArrayNode();
                        Iterator var18 = process.getLanes().iterator();

                        while(var18.hasNext()) {
                            Lane lane = (Lane)var18.next();
                            ObjectNode laneNode = this.objectMapper.createObjectNode();
                            laneNode.put("id", lane.getId());
                            laneNode.put("name", lane.getName());
                            this.fillGraphicInfo(laneNode, pojoModel.getGraphicInfo(lane.getId()), true);
                            laneArray.add(laneNode);
                        }

                        poolNode.set("lanes", laneArray);
                    }

                    poolArray.add(poolNode);
                    double rightX = poolInfo.getX() + poolInfo.getWidth();
                    double bottomY = poolInfo.getY() + poolInfo.getHeight();
                    double middleX = poolInfo.getX() + poolInfo.getWidth() / 2.0D;
                    if (firstElement || middleX < diagramInfo.getX()) {
                        diagramInfo.setX(middleX);
                    }

                    if (firstElement || poolInfo.getY() < diagramInfo.getY()) {
                        diagramInfo.setY(poolInfo.getY());
                    }

                    if (rightX > diagramInfo.getWidth()) {
                        diagramInfo.setWidth(rightX);
                    }

                    if (bottomY > diagramInfo.getHeight()) {
                        diagramInfo.setHeight(bottomY);
                    }
                }

                displayNode.set("pools", poolArray);
            } else {
                diagramInfo.setX(9999.0D);
                diagramInfo.setY(1000.0D);
            }

            Iterator var23 = pojoModel.getProcesses().iterator();

            while(var23.hasNext()) {
                Process process = (Process)var23.next();
                this.processElements(process.getFlowElements(), pojoModel, elementArray, flowArray, collapsedArray, diagramInfo, completedElements, currentElements, (ObjectNode)null);
            }

            displayNode.set("elements", elementArray);
            displayNode.set("flows", flowArray);
            displayNode.set("collapsed", collapsedArray);
        }
    }

    protected void processElements(Collection<FlowElement> elementList, BpmnModel model, ArrayNode elementArray, ArrayNode flowArray, ArrayNode collapsedArray, GraphicInfo diagramInfo, Set<String> completedElements, Set<String> currentElements, ObjectNode collapsedNode) {
        Iterator var10 = elementList.iterator();

        while(true) {
            while(var10.hasNext()) {
                FlowElement element = (FlowElement)var10.next();
                ObjectNode elementNode = this.objectMapper.createObjectNode();
                if (completedElements != null) {
                    elementNode.put("completed", completedElements.contains(element.getId()));
                }

                if (currentElements != null) {
                    elementNode.put("current", currentElements.contains(element.getId()));
                }

                if (element instanceof SequenceFlow) {
                    SequenceFlow flow = (SequenceFlow)element;
                    elementNode.put("id", flow.getId());
                    elementNode.put("type", "sequenceFlow");
                    elementNode.put("sourceRef", flow.getSourceRef());
                    elementNode.put("targetRef", flow.getTargetRef());
                    elementNode.put("name", flow.getName());
                    List<GraphicInfo> flowInfo = model.getFlowLocationGraphicInfo(flow.getId());
                    ArrayNode waypointArray = this.objectMapper.createArrayNode();
                    Iterator var27 = flowInfo.iterator();

                    while(var27.hasNext()) {
                        GraphicInfo graphicInfo = (GraphicInfo)var27.next();
                        ObjectNode pointNode = this.objectMapper.createObjectNode();
                        this.fillGraphicInfo(pointNode, graphicInfo, false);
                        waypointArray.add(pointNode);
                        this.fillDiagramInfo(graphicInfo, diagramInfo);
                    }

                    elementNode.set("waypoints", waypointArray);
                    if (collapsedNode != null) {
                        ((ArrayNode)collapsedNode.get("flows")).add(elementNode);
                    } else {
                        flowArray.add(elementNode);
                    }
                } else {
                    elementNode.put("id", element.getId());
                    elementNode.put("name", element.getName());
                    if (element instanceof FlowNode) {
                        FlowNode flowNode = (FlowNode)element;
                        ArrayNode incomingFlows = this.objectMapper.createArrayNode();
                        Iterator var15 = flowNode.getIncomingFlows().iterator();

                        while(var15.hasNext()) {
                            SequenceFlow flow = (SequenceFlow)var15.next();
                            incomingFlows.add(flow.getId());
                        }

                        elementNode.set("incomingFlows", incomingFlows);
                    }

                    GraphicInfo graphicInfo = model.getGraphicInfo(element.getId());
                    if (graphicInfo != null) {
                        this.fillGraphicInfo(elementNode, graphicInfo, true);
                        this.fillDiagramInfo(graphicInfo, diagramInfo);
                    }

                    String className = element.getClass().getSimpleName();
                    elementNode.put("type", className);
                    this.fillEventTypes(className, element, elementNode);
                    if (element instanceof ServiceTask) {
                        ServiceTask serviceTask = (ServiceTask)element;
                        if ("mail".equals(serviceTask.getType())) {
                            elementNode.put("taskType", "mail");
                        }
                    }

                    if (this.propertyMappers.containsKey(className)) {
                        elementNode.set("properties", ((InfoMapper)this.propertyMappers.get(className)).map(element));
                    }

                    if (collapsedNode != null) {
                        ((ArrayNode)collapsedNode.get("elements")).add(elementNode);
                    } else {
                        elementArray.add(elementNode);
                    }

                    if (element instanceof SubProcess) {
                        SubProcess subProcess = (SubProcess)element;
                        ObjectNode newCollapsedNode = collapsedNode;
                        if (graphicInfo != null && graphicInfo.getExpanded() != null && !graphicInfo.getExpanded()) {
                            elementNode.put("collapsed", "true");
                            newCollapsedNode = this.objectMapper.createObjectNode();
                            newCollapsedNode.put("id", subProcess.getId());
                            newCollapsedNode.putArray("elements");
                            newCollapsedNode.putArray("flows");
                            collapsedArray.add(newCollapsedNode);
                        }

                        this.processElements(subProcess.getFlowElements(), model, elementArray, flowArray, collapsedArray, diagramInfo, completedElements, currentElements, newCollapsedNode);
                    }
                }
            }

            return;
        }
    }

    protected void fillEventTypes(String className, FlowElement element, ObjectNode elementNode) {
        if (this.eventElementTypes.contains(className)) {
            Event event = (Event)element;
            if (!CollectionUtils.isEmpty(event.getEventDefinitions())) {
                EventDefinition eventDef = (EventDefinition)event.getEventDefinitions().get(0);
                ObjectNode eventNode = this.objectMapper.createObjectNode();
                if (eventDef instanceof TimerEventDefinition) {
                    TimerEventDefinition timerDef = (TimerEventDefinition)eventDef;
                    eventNode.put("type", "timer");
                    if (StringUtils.isNotEmpty(timerDef.getTimeCycle())) {
                        eventNode.put("timeCycle", timerDef.getTimeCycle());
                    }

                    if (StringUtils.isNotEmpty(timerDef.getTimeDate())) {
                        eventNode.put("timeDate", timerDef.getTimeDate());
                    }

                    if (StringUtils.isNotEmpty(timerDef.getTimeDuration())) {
                        eventNode.put("timeDuration", timerDef.getTimeDuration());
                    }
                } else if (eventDef instanceof ConditionalEventDefinition) {
                    ConditionalEventDefinition conditionalDef = (ConditionalEventDefinition)eventDef;
                    eventNode.put("type", "conditional");
                    if (StringUtils.isNotEmpty(conditionalDef.getConditionExpression())) {
                        eventNode.put("condition", conditionalDef.getConditionExpression());
                    }
                } else if (eventDef instanceof ErrorEventDefinition) {
                    ErrorEventDefinition errorDef = (ErrorEventDefinition)eventDef;
                    eventNode.put("type", "error");
                    if (StringUtils.isNotEmpty(errorDef.getErrorCode())) {
                        eventNode.put("errorCode", errorDef.getErrorCode());
                    }
                } else if (eventDef instanceof EscalationEventDefinition) {
                    EscalationEventDefinition escalationDef = (EscalationEventDefinition)eventDef;
                    eventNode.put("type", "escalation");
                    if (StringUtils.isNotEmpty(escalationDef.getEscalationCode())) {
                        eventNode.put("escalationCode", escalationDef.getEscalationCode());
                    }
                } else if (eventDef instanceof SignalEventDefinition) {
                    SignalEventDefinition signalDef = (SignalEventDefinition)eventDef;
                    eventNode.put("type", "signal");
                    if (StringUtils.isNotEmpty(signalDef.getSignalRef())) {
                        eventNode.put("signalRef", signalDef.getSignalRef());
                    }
                } else if (eventDef instanceof MessageEventDefinition) {
                    MessageEventDefinition messageDef = (MessageEventDefinition)eventDef;
                    eventNode.put("type", "message");
                    if (StringUtils.isNotEmpty(messageDef.getMessageRef())) {
                        eventNode.put("messageRef", messageDef.getMessageRef());
                    }
                }

                elementNode.set("eventDefinition", eventNode);
            }
        }

    }

    protected void fillGraphicInfo(ObjectNode elementNode, GraphicInfo graphicInfo, boolean includeWidthAndHeight) {
        this.commonFillGraphicInfo(elementNode, graphicInfo.getX(), graphicInfo.getY(), graphicInfo.getWidth(), graphicInfo.getHeight(), includeWidthAndHeight);
    }

    protected void commonFillGraphicInfo(ObjectNode elementNode, double x, double y, double width, double height, boolean includeWidthAndHeight) {
        elementNode.put("x", x);
        elementNode.put("y", y);
        if (includeWidthAndHeight) {
            elementNode.put("width", width);
            elementNode.put("height", height);
        }

    }

    protected void fillDiagramInfo(GraphicInfo graphicInfo, GraphicInfo diagramInfo) {
        double rightX = graphicInfo.getX() + graphicInfo.getWidth();
        double bottomY = graphicInfo.getY() + graphicInfo.getHeight();
        double middleX = graphicInfo.getX() + graphicInfo.getWidth() / 2.0D;
        if (middleX < diagramInfo.getX()) {
            diagramInfo.setX(middleX);
        }

        if (graphicInfo.getY() < diagramInfo.getY()) {
            diagramInfo.setY(graphicInfo.getY());
        }

        if (rightX > diagramInfo.getWidth()) {
            diagramInfo.setWidth(rightX);
        }

        if (bottomY > diagramInfo.getHeight()) {
            diagramInfo.setHeight(bottomY);
        }

    }
}
