package com.heshaowei.myproj.account.controller;

import com.heshaowei.myproj.account.bean.PageReq;
import com.heshaowei.myproj.account.bean.PageResult;
import com.heshaowei.myproj.account.domain.User;
import com.heshaowei.myproj.account.service.UserService;
import com.heshaowei.myproj.bean.response.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/findByUsername")
    public Result findByUsername(String username){
        return Result.success(this.userService.findByUsername(username));
    }

    @GetMapping("/selectPage")
    public Page selectPage(int current, int size){
        Page page = this.userService.selectPage(PageRequest.of(current - 1, size));
        return page;
    }

    @PostMapping("/save")
    public Result save(@RequestBody User user){
        this.userService.save(user);
        return Result.success();
    }

    @GetMapping("/selectById")
    public Result selectById(Long id){
        User user = this.userService.selectById(id);
        return Result.success(user);
    }

    @PutMapping("/batchDelete")
    public Result batchDelete(@RequestBody List<Long> ids){
        this.userService.batchDelete(ids);
        return Result.success();
    }
}
