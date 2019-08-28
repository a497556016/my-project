package com.heshaowei.myproj.im.server.io;

import com.heshaowei.myproj.im.server.model.Message;

public interface MessageRepository {
    void save(Message message);
}
