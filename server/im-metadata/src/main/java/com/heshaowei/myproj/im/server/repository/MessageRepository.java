package com.heshaowei.myproj.im.server.repository;

import com.heshaowei.myproj.im.server.model.Message;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRepository extends MongoRepository<Message, Long> {
}
