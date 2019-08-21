package com.heshaowei.myproj.account.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
@NoArgsConstructor
public class RolePermissionsDTO extends RoleDTO<RolePermissionsDTO> {
    private List<PermissionDTO> permissions;
}
