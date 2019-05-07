package com.heshaowei.myproj.account.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.google.common.base.Converter;
import com.heshaowei.myproj.account.domain.User;
import lombok.*;
import lombok.experimental.Accessors;
import org.springframework.beans.BeanUtils;

import javax.validation.constraints.NotBlank;
import java.util.Date;

@Accessors(chain = true)
@Data
@Builder
public class UserDTO extends Converter<UserDTO, User> {

    private Long id;

    @NotBlank(message = "用户名不能为空！")
    private String username;

    @NotBlank(message = "密码不能为空！")
    private String password;

    private String salt;

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

    public User convert(){
        return this.convert(this);
    }

    @Override
    protected User doForward(UserDTO userDTO) {
        User user = new User();
        BeanUtils.copyProperties(userDTO, user);
        return user;
    }

    @Override
    protected UserDTO doBackward(User user) {
        UserDTO userDTO = UserDTO.builder().build();
        BeanUtils.copyProperties(user, userDTO);
        return userDTO;
    }
}
