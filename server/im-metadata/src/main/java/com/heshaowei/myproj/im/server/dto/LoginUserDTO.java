package com.heshaowei.myproj.im.server.dto;

import lombok.Data;

import java.util.Date;

@Data
public class LoginUserDTO {
    private String id;
    private String username;
    private String nickname;
    private String avatar;
    private String phone;
    private String token;
    private Date createTime;
}
