package com.heshaowei.myproj.account.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.heshaowei.myproj.account.entity.base.BaseEntity;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@Entity
public class User extends BaseEntity implements Serializable {

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

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Role> roles;
}
