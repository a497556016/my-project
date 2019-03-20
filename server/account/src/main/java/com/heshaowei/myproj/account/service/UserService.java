package com.heshaowei.myproj.account.service;

import com.heshaowei.myproj.account.bean.PageReq;
import com.heshaowei.myproj.account.domain.User;
import com.heshaowei.myproj.account.repository.UserRepository;
import com.heshaowei.myproj.utils.PropertyCopys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User findByUsername(String username) {
        User user = new User();
        user.setUsername(username);
        return this.userRepository.findOne(Example.of(user)).orElse(null);
    }

    public void save(User user) {
        this.userRepository.save(user);
    }

    public Page selectPage(PageRequest pageReq) {
        return this.userRepository.findAll(pageReq);
    }

    public User selectById(Long id) {
        return this.userRepository.findById(id).orElse(null);
    }

    public void update(User user) {
        User updateUser = this.userRepository.findById(user.getId()).orElse(new User());
        PropertyCopys.copyNotnull(updateUser, user);
        this.userRepository.saveAndFlush(updateUser);
    }
}
