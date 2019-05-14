package com.heshaowei.myproj.cusform.service;

import com.heshaowei.myproj.cusform.entity.mongo.UserForm;
import com.heshaowei.myproj.cusform.repository.UserFormRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserFormService {
    @Autowired
    private UserFormRepository userFormRepository;

    public void save(UserForm userForm) {
        this.userFormRepository.save(userForm);
    }
}
