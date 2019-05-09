package com.heshaowei.myproj.cusform.repository;

import com.heshaowei.myproj.cusform.entity.mongo.CustomerForm;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerFormRepository extends MongoRepository<CustomerForm, Long> {
}
