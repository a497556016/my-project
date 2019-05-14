package com.heshaowei.myproj.cusform.entity.mongo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Data;
import lombok.experimental.Accessors;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

/**
 * 用户提交的表单
 */
@Document
@Data
@Accessors(chain = true)
public class UserForm {
    private ObjectId id;
    private ObjectId formId;
    private String username;
//    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    private List<CustomerFormItem> items;
}
