package com.heshaowei.myproj.account.controller;

import com.google.common.collect.Maps;
import com.heshaowei.myproj.account.entity.User;
import com.heshaowei.myproj.account.service.UserService;
import com.heshaowei.myproj.bean.response.Result;
import com.heshaowei.myproj.utils.PasswordEncode;
import com.heshaowei.myproj.utils.token.JWTUtil;
import com.heshaowei.myproj.utils.token.TokenResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/account")
public class AccountController {
    private static long EXPIRE_TIME = 30 * 60 * 1000;

    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public Result login(String username, String password){
        User user = this.userService.findByUsername(username);
        if(null == user) {
            return Result.error("用户名不存在！");
        }
        if(PasswordEncode.verify(password, user.getSalt(), user.getPassword())){
            TokenResponse tr = JWTUtil.sign(user.getUsername(), user.getPassword(), EXPIRE_TIME);
            Map<String, Object> accountInfo = Maps.newHashMap();
            accountInfo.put("avatar", user.getAvatar());
            accountInfo.put("accessToken", tr.getAccessToken());
            accountInfo.put("expireTime", tr.getExpireTime());
            return Result.success(accountInfo);
        }else {
            return Result.error("密码错误！");
        }
    }

    @PostMapping("/regist")
    public Result regist(@Validated @RequestBody User user, BindingResult br){
        if(br.hasErrors()){
            return Result.error(br);
        }

        User u = this.userService.findByUsername(user.getUsername());
        if(null != u) {
            return Result.error("用户名已经存在！");
        }

        String salt = UUID.randomUUID().toString();
        String password = PasswordEncode.encrypt(user.getPassword(), salt);

        user.setSalt(salt);
        user.setPassword(password);

        this.userService.save(user);

        return Result.success();
    }

    @PutMapping("/modify")
    public Result modify(@RequestBody User user){
        if(StringUtils.isNotBlank(user.getPassword())) {
            String salt = UUID.randomUUID().toString();
            String password = PasswordEncode.encrypt(user.getPassword(), salt);

            user.setSalt(salt);
            user.setPassword(password);
        }else{
            user.setPassword(null);
        }

        this.userService.update(user);

        return Result.success();
    }

    @GetMapping("/refreshToken")
    public Result refreshToken(String accessToken){
        String username = JWTUtil.getUsername(accessToken);
        User managerInfo = this.userService.findByUsername(username);

        return Result.success(JWTUtil.sign(username, managerInfo.getPassword(), EXPIRE_TIME));
    }
}
