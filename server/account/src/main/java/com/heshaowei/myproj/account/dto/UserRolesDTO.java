package com.heshaowei.myproj.account.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.heshaowei.myproj.account.entity.User;
import com.heshaowei.myproj.base.MyConverter;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import java.util.Date;
import java.util.List;

@Accessors(chain = true)
@Data
@NoArgsConstructor
public class UserRolesDTO extends UserDTO<UserRolesDTO> {

    private List<RoleBaseDTO> roles;
}
