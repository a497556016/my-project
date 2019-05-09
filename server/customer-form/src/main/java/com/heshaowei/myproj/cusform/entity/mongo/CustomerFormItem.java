package com.heshaowei.myproj.cusform.entity.mongo;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;
import java.util.Map;

@Document
@Data
public class CustomerFormItem {
    @Id
    private ObjectId id;
    private String name;
    private String question;
    private String component;
    private Boolean disabled;
    private Map<String, Object> meta;
}
