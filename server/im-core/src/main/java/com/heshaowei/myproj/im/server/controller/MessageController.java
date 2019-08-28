package com.heshaowei.myproj.im.server.controller;

import com.heshaowei.myproj.bean.response.Result;
import com.heshaowei.myproj.im.server.model.User;
import com.heshaowei.myproj.im.server.model.UserMessage;
import com.heshaowei.myproj.im.server.repository.GroupMessageRepository;
import com.heshaowei.myproj.im.server.repository.UserMessageRepository;
import com.heshaowei.myproj.im.server.utils.LoginUserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
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
            /*UserMessage probe = new UserMessage();
            User to = new User();
            to.setUsername(lineUsername);
            probe.setTo(to);
            User from = new User();
            from.setUsername(loginUsername);*/
            Sort sort = Sort.by(Sort.Direction.ASC,"time");
            Page<UserMessage> page = this.userMessageRepository.selectHisUserMessages(loginUsername, lineUsername, PageRequest.of(0, 10, sort));
            return Result.success(page);
        }
        return Result.error();
    }
}
