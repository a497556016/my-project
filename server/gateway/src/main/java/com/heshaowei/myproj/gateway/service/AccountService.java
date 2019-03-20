package com.heshaowei.myproj.gateway.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("account-server")
public interface AccountService {

    @GetMapping("/user/findByUsername")
    String findByUsername(@RequestParam("username") String username);
}
