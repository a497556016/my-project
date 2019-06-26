package com.heshaowei.myproj.gateway.service;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;

@CacheConfig(cacheNames = "user")
public interface IAccountService {

    @Cacheable(key = "#username")
    String findByUsername(String username);
}
