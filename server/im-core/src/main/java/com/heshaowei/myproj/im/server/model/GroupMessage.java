package com.heshaowei.myproj.im.server.model;

import com.google.gson.Gson;
import com.heshaowei.myproj.im.server.utils.GsonUtil;
import lombok.Data;

@Data
public class GroupMessage extends Message {
    private Group to;

    public static GroupMessage buildFromJson(String json) {
        GroupMessage gm = GsonUtil.get().fromJson(json, GroupMessage.class);
        return gm;
    }
}
