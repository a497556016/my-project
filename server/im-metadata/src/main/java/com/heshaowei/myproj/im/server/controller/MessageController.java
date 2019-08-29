package com.heshaowei.myproj.im.server.controller;

import com.google.common.collect.Lists;
import com.heshaowei.myproj.bean.response.Result;
import com.heshaowei.myproj.im.server.enums.MessageStates;
import com.heshaowei.myproj.im.server.model.GroupMessage;
import com.heshaowei.myproj.im.server.model.User;
import com.heshaowei.myproj.im.server.model.UserMessage;
import com.heshaowei.myproj.im.server.repository.GroupMessageRepository;
import com.heshaowei.myproj.im.server.repository.UserMessageRepository;
import com.heshaowei.myproj.im.server.utils.GsonUtil;
import com.heshaowei.myproj.im.server.utils.LoginUserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/message")
public class MessageController {
    @Autowired
    private UserMessageRepository userMessageRepository;

    @Autowired
    private GroupMessageRepository groupMessageRepository;

    @GetMapping("/queryHisUserMessages")
    public Result<Page<UserMessage>> queryHisUserMessages(String loginUsername, String lineUsername){
        if(null != loginUsername && null != lineUsername) {
            Sort sort = Sort.by(Sort.Direction.DESC,"time");
            Page<UserMessage> page = this.userMessageRepository.selectHisUserMessages(loginUsername, lineUsername, PageRequest.of(0, 10, sort));
            return Result.success(page);
        }
        return Result.error();
    }

    @GetMapping("/queryNotReceiveMessages")
    public Result<Page<UserMessage>> queryNotReceiveMessages(String loginUsername, String lineUsername){
        if(null != loginUsername && null != lineUsername) {
            Sort sort = Sort.by(Sort.Direction.DESC,"time");
            Page<UserMessage> page = this.userMessageRepository.selectNotReceiveMessages(loginUsername, lineUsername, PageRequest.of(0, 10, sort));
            //修改状态
            page.getContent().forEach(m -> m.setSendState(MessageStates.SUCCESS));
            this.userMessageRepository.saveAll(page.getContent());
            return Result.success(page);
        }
        return Result.error();
    }

    @PostMapping("/saveUserMessage")
    public Result saveUserMessage(@RequestParam("message") String messageJson){
        UserMessage message = GsonUtil.get().fromJson(messageJson, UserMessage.class);
        this.userMessageRepository.save(message);
        return Result.success();
    }

    @PostMapping("/saveGroupMessage")
    public Result saveGroupMessage(@RequestParam("message") String messageJson){
        GroupMessage message = GsonUtil.get().fromJson(messageJson, GroupMessage.class);
        this.groupMessageRepository.save(message);
        return Result.success();
    }
}
