package com.heshaowei.myproj.auth.core;

import com.heshaowei.myproj.auth.core.model.UserInfo;

public interface UserService {

    /**
     * 查询用户信息，在系统登录时调用
     *
     * @param username
     * @return
     */
    UserInfo selectByUsername(String username);

}
