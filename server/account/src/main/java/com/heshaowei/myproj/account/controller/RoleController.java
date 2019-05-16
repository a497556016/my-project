package com.heshaowei.myproj.account.controller;

import com.heshaowei.myproj.account.controller.base.CurdController;
import com.heshaowei.myproj.account.dto.RoleDTO;
import com.heshaowei.myproj.account.entity.Role;
import com.heshaowei.myproj.account.repository.RoleRepository;
import com.heshaowei.myproj.account.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/role")
public class RoleController extends CurdController<RoleRepository, Role, RoleDTO> {

    @Autowired
    private RoleService roleService;

}
