package com.heshaowei.myproj.im.metadata.client.controller;

import com.heshaowei.myproj.bean.response.Result;
import com.heshaowei.myproj.im.metadata.client.config.Global;
import com.heshaowei.myproj.im.metadata.client.controller.impl.MessageClientImpl;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = Global.APP_NAME, fallback = MessageClientImpl.class)
public interface MessageClient {

    @PostMapping("/message/saveUserMessage")
    Result saveUserMessage(@RequestParam("message") String message);

    @PostMapping("/message/saveGroupMessage")
    Result saveGroupMessage(@RequestParam("message") String message);
}
