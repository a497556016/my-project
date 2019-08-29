package com.heshaowei.myproj.im.server.repository;

import com.heshaowei.myproj.im.server.model.UserMessage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMessageRepository extends MongoRepository<UserMessage, String> {

    @Query(value = "{'$or':[{'$and':[{'from.username': ?0},{'to.username': ?1}]},{'$and':[{'from.username': ?1},{'to.username': ?0}]}]}")
    Page<UserMessage> selectHisUserMessages(String loginUsername, String lineUsername, Pageable pageable);

    @Query(value = "{'$or':[{'$and':[{'from.username': ?1},{'to.username': ?0},{'sendState': 'NOT_RECEIVE'}]}]}")
    Page<UserMessage> selectNotReceiveMessages(String loginUsername, String lineUsername, Pageable pageable);
}
