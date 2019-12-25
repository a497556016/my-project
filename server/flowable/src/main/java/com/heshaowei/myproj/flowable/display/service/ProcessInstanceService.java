package com.heshaowei.myproj.flowable.display.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.heshaowei.myproj.flowable.display.domain.ServerConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProcessInstanceService {
    @Autowired
    private FlowableClientService clientUtil;

    @Autowired
    private ServerConfig serverConfig;

    public List<String> getCompletedActivityInstancesAndProcessDefinitionId(String processInstanceId) {
        URIBuilder builder = this.clientUtil.createUriBuilder("history/historic-activity-instances");
        builder.addParameter("processInstanceId", processInstanceId);
        builder.addParameter("sort", "startTime");
        builder.addParameter("order", "asc");
        builder.addParameter("size", "1024");
        HttpGet get = new HttpGet(this.clientUtil.getServerUrl(serverConfig, builder));
        JsonNode node = this.clientUtil.executeRequest(get);
        List<String> result = new ArrayList();
        if (node.has("data") && node.get("data").isArray()) {
            ArrayNode data = (ArrayNode)node.get("data");
            ObjectNode activity = null;

            for(int i = 0; i < data.size(); ++i) {
                activity = (ObjectNode)data.get(i);
                if (activity.has("activityType")) {
                    result.add(activity.get("activityId").asText());
                }
            }
        }

        return result;
    }

    public List<String> getCurrentActivityInstances(String processInstanceId) {
        URIBuilder builder = this.clientUtil.createUriBuilder(MessageFormat.format("runtime/executions/{0}/activities", processInstanceId));
        HttpGet get = new HttpGet(this.clientUtil.getServerUrl(serverConfig, builder));
        JsonNode node = this.clientUtil.executeRequest(get);
        List<String> result = new ArrayList();
        if (node.isArray()) {
            ArrayNode data = (ArrayNode)node;

            for(int i = 0; i < data.size(); ++i) {
                if (data.get(i) != null) {
                    result.add(data.get(i).asText());
                }
            }
        }

        return result;
    }
}
