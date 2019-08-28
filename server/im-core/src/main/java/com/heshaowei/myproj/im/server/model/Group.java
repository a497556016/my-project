package com.heshaowei.myproj.im.server.model;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

/**
 * 群组
 */
@Document(collection = "im_group")
@Data
public class Group {
    @Id
    private ObjectId id;
    private String name;
    List<User> users;
}
