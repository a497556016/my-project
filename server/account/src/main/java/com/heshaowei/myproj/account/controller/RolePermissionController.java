package com.heshaowei.myproj.account.controller;

import com.heshaowei.myproj.account.dto.RolePermissionsDTO;
import com.heshaowei.myproj.account.entity.Role;
import com.heshaowei.myproj.account.repository.RoleRepository;
import com.heshaowei.myproj.account.service.RoleService;
import com.heshaowei.myproj.base.CurdController;
import com.heshaowei.myproj.bean.response.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/role-permission")
public class RolePermissionController extends CurdController<RoleRepository, Role, RolePermissionsDTO> {

    @Autowired
    private RoleService roleService;

    @PutMapping("/assignPermissions")
    public Result assignPermissions(@RequestBody RolePermissionsDTO rolePermissions){
        Role role = rolePermissions.convert(rolePermissions);
        this.roleService.updateRolePermissions(role);
        return Result.success();
    }
}
