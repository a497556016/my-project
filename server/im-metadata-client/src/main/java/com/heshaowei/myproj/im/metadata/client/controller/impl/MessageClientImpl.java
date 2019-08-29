package com.heshaowei.myproj.im.metadata.client.controller.impl;

import com.heshaowei.myproj.bean.response.Result;
import com.heshaowei.myproj.im.metadata.client.controller.MessageClient;
import org.springframework.stereotype.Service;

@Service
public class MessageClientImpl implements MessageClient {
    @Override
    public Result saveUserMessage(String message) {
        return Result.error("系统异常");
    }

    @Override
    public Result saveGroupMessage(String message) {
        return Result.error("系统异常");
    }
}
