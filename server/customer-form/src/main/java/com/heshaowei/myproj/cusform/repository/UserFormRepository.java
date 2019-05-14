package com.heshaowei.myproj.cusform.repository;

import com.heshaowei.myproj.cusform.entity.mongo.UserForm;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserFormRepository extends MongoRepository<UserForm, ObjectId> {
}
