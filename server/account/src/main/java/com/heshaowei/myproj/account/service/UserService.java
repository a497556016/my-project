package com.heshaowei.myproj.account.service;

import com.google.common.collect.Lists;
import com.heshaowei.myproj.account.entity.User;
import com.heshaowei.myproj.account.repository.UserRepository;
import com.heshaowei.myproj.utils.PropertyCopys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private IFileService fileService;

    private void updateFileBusId(String path, Long id){
        if(null != path) {
            this.fileService.updateBusId(new IFileService.FileReq(path, "account-server/user/" + id));
        }
    }

    public User findByUsername(String username) {
        User user = new User();
        user.setUsername(username);
        return this.userRepository.findOne(Example.of(user)).orElse(null);
    }

    public void save(User user) {
        user = this.userRepository.save(user);

        this.updateFileBusId(user.getAvatar(), user.getId());
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
        this.updateFileBusId(user.getAvatar(), user.getId());
    }

    public void batchDelete(List<Long> ids) {
        final List<User> users = Lists.newArrayList();
        ids.forEach(id -> {
            User user = new User();
            user.setId(id);
            users.add(user);
        });
        this.userRepository.deleteInBatch(users);
    }
}
