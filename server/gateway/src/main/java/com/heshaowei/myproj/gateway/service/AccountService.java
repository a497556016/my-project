package com.heshaowei.myproj.gateway.service;

import com.heshaowei.myproj.account.client.controller.UserClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
@CacheConfig(cacheNames = "user")
public class AccountService {

    @Autowired
    private UserClient userClient;

    @Cacheable(key = "#username")
    public String findByUsername(String username) {
        return this.userClient.findByUsername(username);
    }

    @CacheEvict(key = "#username")
    public void removeByUsername(String username) {

    }

    public boolean verify(String accessToken, String path) {
        return this.userClient.verify(accessToken, path);
    }
}
