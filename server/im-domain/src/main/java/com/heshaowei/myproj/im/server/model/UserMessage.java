package com.heshaowei.myproj.im.server.model;

import com.heshaowei.myproj.im.server.utils.GsonUtil;
import lombok.Data;

@Data
public class UserMessage extends Message {
    private User to;

    public static UserMessage buildFromJson(String json){
        UserMessage um = GsonUtil.get().fromJson(json, UserMessage.class);
        return um;
    }
}
