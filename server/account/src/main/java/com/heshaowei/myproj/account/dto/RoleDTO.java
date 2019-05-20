package com.heshaowei.myproj.account.dto;

import com.heshaowei.myproj.account.entity.Role;
import com.heshaowei.myproj.base.MyConverter;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;
import java.util.List;

@Data
@Accessors(chain = true)
@NoArgsConstructor
public class RoleDTO extends MyConverter<RoleDTO, Role> {

    private Long id;
    private String name;
    private String code;
    private String creater;
    private Date createTime;

    private List<UserDTO> userList;
}
