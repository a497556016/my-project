package com.heshaowei.myproj.im.server.io;

import com.heshaowei.myproj.im.server.model.Message;

public interface MessageRepository {

    void push(Message message);

    void save(Message message);
}
