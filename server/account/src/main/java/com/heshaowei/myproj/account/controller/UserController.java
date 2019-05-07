package com.heshaowei.myproj.account.controller;

import com.google.common.collect.Lists;
import com.heshaowei.myproj.account.domain.User;
import com.heshaowei.myproj.account.dto.UserDTO;
import com.heshaowei.myproj.account.service.UserService;
import com.heshaowei.myproj.bean.response.PageResult;
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
    public PageResult<UserDTO> selectPage(int current, int size){
        Page<User> page = this.userService.selectPage(PageRequest.of(current - 1, size));
        Iterable<UserDTO> records = UserDTO.builder().build().reverse().convertAll(page.getContent());
        return PageResult.of(UserDTO.class, current, size).success(Lists.newArrayList(records), page.getTotalElements());
    }

    @PostMapping("/save")
    public Result save(@RequestBody UserDTO user){
        this.userService.save(user.convert());
        return Result.success();
    }

    @GetMapping("/selectById")
    public Result selectById(Long id){
        User user = this.userService.selectById(id);
        UserDTO userDTO = UserDTO.builder().build().reverse().convert(user);
        return Result.success(userDTO);
    }

    @PutMapping("/batchDelete")
    public Result batchDelete(@RequestBody List<Long> ids){
        this.userService.batchDelete(ids);
        return Result.success();
    }
}
