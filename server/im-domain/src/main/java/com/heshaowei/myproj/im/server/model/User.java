package com.heshaowei.myproj.im.server.model;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "im_user")
@Data
public class User {
    @Id
    private ObjectId id;
    private String username;
    private String password;
    private String nickname;
    private String avatar;
    private String phone;
}
