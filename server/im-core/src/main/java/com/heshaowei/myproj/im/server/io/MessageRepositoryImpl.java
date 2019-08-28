package com.heshaowei.myproj.im.server.io;

import com.heshaowei.myproj.im.server.model.Group;
import com.heshaowei.myproj.im.server.model.GroupMessage;
import com.heshaowei.myproj.im.server.model.Message;
import com.heshaowei.myproj.im.server.model.UserMessage;
import com.heshaowei.myproj.im.server.repository.GroupMessageRepository;
import com.heshaowei.myproj.im.server.repository.UserMessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageRepositoryImpl implements MessageRepository {

    @Autowired
    private UserMessageRepository userMessageRepository;
    @Autowired
    private GroupMessageRepository groupMessageRepository;

    @Override
    public void save(Message message) {
        if(message instanceof UserMessage) {
            this.userMessageRepository.save((UserMessage) message);
        }
        if(message instanceof GroupMessage) {
            this.groupMessageRepository.save((GroupMessage) message);
        }
    }
}
