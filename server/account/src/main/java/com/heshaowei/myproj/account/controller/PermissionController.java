package com.heshaowei.myproj.account.controller;

import com.heshaowei.myproj.account.dto.PermissionBaseDTO;
import com.heshaowei.myproj.account.entity.Permission;
import com.heshaowei.myproj.account.repository.PermissionRepository;
import com.heshaowei.myproj.base.CurdController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/permission")
public class PermissionController extends CurdController<PermissionRepository, Permission, PermissionBaseDTO> {
}
