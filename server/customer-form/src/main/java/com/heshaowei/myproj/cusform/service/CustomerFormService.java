package com.heshaowei.myproj.cusform.service;

import com.heshaowei.myproj.cusform.entity.mongo.CustomerForm;
import com.heshaowei.myproj.cusform.repository.CustomerFormRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class CustomerFormService {
    @Autowired
    private CustomerFormRepository formRepository;

    public void save(CustomerForm form) {
        form.setCreateTime(new Date());
        form.getFormItems().forEach(item -> {
            item.setId(new ObjectId());
        });
        this.formRepository.save(form);
    }

    public Page<CustomerForm> selectPage(PageRequest pr) {
        return this.formRepository.findAll(pr);
    }

    public CustomerForm selectById(String id) {
        return this.formRepository.findById(new ObjectId(id)).get();
    }

    public void update(CustomerForm form) {
        form.setUpdateTime(new Date());
        this.formRepository.save(form);
    }
}
