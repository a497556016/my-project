package com.heshaowei.myproj.account.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.google.common.base.Converter;
import com.google.common.collect.Lists;
import com.heshaowei.myproj.account.entity.Role;
import com.heshaowei.myproj.account.entity.User;
import com.heshaowei.myproj.base.MyConverter;
import lombok.*;
import lombok.experimental.Accessors;
import org.springframework.beans.BeanUtils;

import javax.validation.constraints.NotBlank;
import java.util.Date;
import java.util.List;

@Accessors(chain = true)
@Data
@NoArgsConstructor
public class UserDTO extends MyConverter<UserDTO, User> {

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

    private List<RoleDTO> roleList;

    public User convert() {
        return this.convert(this);
    }

    @Override
    protected User doForward(UserDTO userDTO) {
        User user = super.doForward(userDTO);

        List<Role> roles = Lists.newArrayList(new RoleDTO().convertAll(userDTO.getRoleList()));
        user.setRoles(roles);

        return user;
    }

    @Override
    protected UserDTO doBackward(User user) {
        UserDTO dto = super.doBackward(user);
        List<RoleDTO> roleDTOS = Lists.newArrayList(new RoleDTO().reverse().convertAll(user.getRoles()));
        dto.setRoleList(roleDTOS);
        dto.setPassword(null);
        return dto;
    }
}
