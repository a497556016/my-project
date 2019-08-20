package com.heshaowei.myproj.account.controller;

import com.heshaowei.myproj.account.dto.RoleUsersDTO;
import com.heshaowei.myproj.account.entity.Role;
import com.heshaowei.myproj.account.repository.RoleRepository;
import com.heshaowei.myproj.base.CurdController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/roleuser")
public class RoleUserController extends CurdController<RoleRepository, Role, RoleUsersDTO> {



}
