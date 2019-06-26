package com.heshaowei.myproj.account.client.controller;

import com.heshaowei.myproj.account.client.config.Global;
import com.heshaowei.myproj.account.client.controller.impl.UserClientImpl;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = Global.APP_NAME, fallback = UserClientImpl.class)
public interface UserClient {

    @GetMapping("/user/findByUsername")
    String findByUsername(@RequestParam("username") String username);
}
