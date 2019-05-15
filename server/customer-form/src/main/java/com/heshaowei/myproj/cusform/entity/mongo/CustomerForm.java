package com.heshaowei.myproj.cusform.entity.mongo;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Document
@Data
public class CustomerForm {
    @Id
    private ObjectId id;
    private String title;
    private String desc;
    private String createBy;
    private Date createTime;
    private String updateBy;
    private Date updateTime;
    private Map<String, Object> meta;
    private List<CustomerFormItem> formItems;
}
