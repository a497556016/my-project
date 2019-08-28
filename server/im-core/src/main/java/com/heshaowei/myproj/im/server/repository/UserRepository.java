package com.heshaowei.myproj.im.server.repository;

import com.heshaowei.myproj.im.server.model.User;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends MongoRepository<User, Long> {
    List<User> findAllByUsernameIsNot(String username, Example<User> example);
}
