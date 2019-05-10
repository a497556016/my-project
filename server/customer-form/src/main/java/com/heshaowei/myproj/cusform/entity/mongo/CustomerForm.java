package com.heshaowei.myproj.cusform.entity.mongo;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;
import java.util.List;

@Document
@Data
public class CustomerForm {
    @Id
    private ObjectId id;
    private String title;
    private String desc;
    private String createBy;
    private List<CustomerFormItem> formItems;
}
