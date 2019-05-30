package com.heshaowei.myproj.account.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

@Accessors(chain = true)
@Data
@NoArgsConstructor
public class UserRolesDTO extends UserDTO {

    private List<RoleDTO> roles;
}
