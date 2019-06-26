package com.heshaowei.myproj.account.client.controller.impl;

import com.heshaowei.myproj.account.client.controller.UserClient;
import org.springframework.stereotype.Component;

@Component
public class UserClientImpl implements UserClient {
    @Override
    public String findByUsername(String username) {
        return "{\"code\": 0, \"msg\": \"查询失败，请稍后重试。\"}";
    }
}
