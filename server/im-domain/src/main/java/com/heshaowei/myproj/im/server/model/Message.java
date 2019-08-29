package com.heshaowei.myproj.im.server.model;

import com.heshaowei.myproj.im.server.enums.MessageStates;
import com.heshaowei.myproj.im.server.enums.MessageTypes;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(collection = "im_message")
@Data
public class Message {
    @Id
    private ObjectId id;
    private MessageTypes type;
    private User from;
    private String msg;
    private Date time;
    /**
     * 发送状态
     */
    private MessageStates sendState;
}
