package com.heshaowei.myproj.account.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.heshaowei.myproj.account.entity.User;
import com.heshaowei.myproj.base.MyConverter;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import java.util.Date;

@Accessors(chain = true)
@Data
@NoArgsConstructor
public class UserDTO<T extends MyConverter> extends MyConverter<T, User> {
    private Long id;

    @NotBlank(message = "用户名不能为空！")
    private String username;

    @NotBlank(message = "密码不能为空！")
    private String password;
//
//    private String salt;

    private String phone;

    private String email;

    private String gender;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date birthday;

    private String avatar;

    private String thumbAvatar;

    private String creater;

    private Date createTime;

    private String modifier;

    private Date modifyTime;

}
