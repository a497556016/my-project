package com.heshaowei.myproj.gateway.service;

import com.heshaowei.myproj.gateway.service.impl.AccountServiceImpl;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "account-server", fallback = AccountServiceImpl.class)
@CacheConfig(cacheNames = "user")
public interface IAccountService {

    @GetMapping("/user/findByUsername")
    @Cacheable(key = "#username")
    String findByUsername(@RequestParam("username") String username);
}
