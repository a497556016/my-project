package com.heshaowei.myproj.gateway.service.impl;

import com.heshaowei.myproj.gateway.service.IAccountService;
import org.springframework.stereotype.Service;

@Service
public class AccountServiceImpl implements IAccountService {
    @Override
    public String findByUsername(String username) {
        return "{\"code\": 0, \"msg\": \"查询失败，请稍后重试。\"}";
    }
}
