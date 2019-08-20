package com.heshaowei.myproj.account.service;

import com.heshaowei.myproj.auth.core.model.UserInfo;
import lombok.Data;

@Data
public class UserDetail extends UserInfo {
    private String avatar;
    private String salt;
}
