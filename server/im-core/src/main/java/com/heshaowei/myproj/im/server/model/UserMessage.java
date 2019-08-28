package com.heshaowei.myproj.im.server.model;

import com.google.gson.*;
import com.heshaowei.myproj.im.server.config.deserializer.ObjectIdDeserializer;
import com.heshaowei.myproj.im.server.utils.GsonUtil;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

import java.lang.reflect.Type;

@Data
public class UserMessage extends Message {
    private User to;

    public static UserMessage buildFromJson(String json){
        UserMessage um = GsonUtil.get().fromJson(json, UserMessage.class);
        return um;
    }
}
