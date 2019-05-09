package com.heshaowei.myproj.cusform.service;

import com.heshaowei.myproj.cusform.entity.mongo.CustomerForm;
import com.heshaowei.myproj.cusform.repository.CustomerFormRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerFormService {
    @Autowired
    private CustomerFormRepository formRepository;

    public void save(CustomerForm form) {
        this.formRepository.save(form);
    }
}
