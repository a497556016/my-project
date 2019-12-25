package com.heshaowei.myproj.flowable.display.clients.modelinfo;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

public abstract class AbstractInfoMapper implements InfoMapper {
    protected DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
    protected ObjectMapper objectMapper = new ObjectMapper();
    protected ArrayNode propertiesNode;

    public AbstractInfoMapper() {
    }

    public ArrayNode map(Object element) {
        this.propertiesNode = this.objectMapper.createArrayNode();
        this.mapProperties(element);
        return this.propertiesNode;
    }

    protected abstract void mapProperties(Object var1);

    protected void createPropertyNode(String name, String value) {
        if (StringUtils.isNotEmpty(value)) {
            ObjectNode propertyNode = this.objectMapper.createObjectNode();
            propertyNode.put("name", name);
            propertyNode.put("value", value);
            this.propertiesNode.add(propertyNode);
        }

    }

    protected void createPropertyNode(String name, Date value) {
        if (value != null) {
            this.createPropertyNode(name, this.dateFormat.format(value));
        }

    }

    protected void createPropertyNode(String name, Boolean value) {
        if (value != null) {
            this.createPropertyNode(name, value.toString());
        }

    }

    protected void createPropertyNode(String name, List<String> values) {
        if (!CollectionUtils.isEmpty(values)) {
            StringBuilder commaSeperatedString = new StringBuilder();

            String value;
            for(Iterator var4 = values.iterator(); var4.hasNext(); commaSeperatedString.append(value)) {
                value = (String)var4.next();
                if (commaSeperatedString.length() > 0) {
                    commaSeperatedString.append(", ");
                }
            }

            this.createPropertyNode(name, commaSeperatedString.toString());
        }

    }
}
