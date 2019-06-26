package com.heshaowei.myproj.gateway.service.impl;

import com.heshaowei.myproj.account.client.controller.UserClient;
import com.heshaowei.myproj.gateway.service.IAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountServiceImpl implements IAccountService {

    @Autowired
    private UserClient userClient;

    @Override
    public String findByUsername(String username) {
        return this.userClient.findByUsername(username);
    }
}
