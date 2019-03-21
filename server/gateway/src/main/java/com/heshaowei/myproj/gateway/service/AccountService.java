package com.heshaowei.myproj.gateway.service;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("account-server")
@CacheConfig(cacheNames = "user")
public interface AccountService {

    @GetMapping("/user/findByUsername")
    @Cacheable(key = "#username")
    String findByUsername(@RequestParam("username") String username);
}
