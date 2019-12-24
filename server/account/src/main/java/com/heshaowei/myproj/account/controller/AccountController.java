package com.heshaowei.myproj.account.controller;

import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.heshaowei.myproj.account.entity.User;
import com.heshaowei.myproj.account.service.UserDetail;
import com.heshaowei.myproj.account.service.UserService;
import com.heshaowei.myproj.auth.core.AccessDecisionManager;
import com.heshaowei.myproj.auth.core.model.UserInfo;
import com.heshaowei.myproj.auth.utils.token.JWTUtil;
import com.heshaowei.myproj.auth.utils.token.TokenResponse;
import com.heshaowei.myproj.bean.response.Result;
import com.heshaowei.myproj.utils.PasswordEncode;
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

    @Autowired
    private AccessDecisionManager accessDecisionManager;

    @GetMapping("/login")
    public Result login(String username, String password) {
        UserDetail user = this.userService.selectByUsername(username);
        if (null == user) {
            return Result.error("用户名不存在！");
        }
        if (PasswordEncode.verify(password, user.getSalt(), user.getPassword())) {
            TokenResponse tr = JWTUtil.sign(new Gson().toJson(user), user.getPassword(), EXPIRE_TIME);
            Map<String, Object> accountInfo = Maps.newHashMap();
            accountInfo.put("avatar", user.getAvatar());
            accountInfo.put("accessToken", tr.getAccessToken());
            accountInfo.put("expireTime", tr.getExpireTime());
            return Result.success(accountInfo);
        } else {
            return Result.error("密码错误！");
        }
    }

    @PostMapping("/regist")
    public Result regist(@Validated @RequestBody User user, BindingResult br) {
        if (br.hasErrors()) {
            return Result.error(br);
        }

        User u = this.userService.findByUsername(user.getUsername());
        if (null != u) {
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
    public Result modify(@RequestBody User user) {
        if (StringUtils.isNotBlank(user.getPassword())) {
            String salt = UUID.randomUUID().toString();
            String password = PasswordEncode.encrypt(user.getPassword(), salt);

            user.setSalt(salt);
            user.setPassword(password);
        } else {
            user.setPassword(null);
        }

        this.userService.update(user);

        return Result.success();
    }

    @GetMapping("/refreshToken")
    public Result refreshToken(String accessToken) {
        String json = JWTUtil.getUserinfo(accessToken);
        UserDetail userInfo = new Gson().fromJson(json, UserDetail.class);
        userInfo = this.userService.selectByUsername(userInfo.getUsername());

        return Result.success(JWTUtil.sign(new Gson().toJson(userInfo), userInfo.getPassword(), EXPIRE_TIME));
    }

    @GetMapping("/verify")
    public boolean verify(String accessToken, String path) {
        return this.accessDecisionManager.verify(accessToken, path);
    }
}
