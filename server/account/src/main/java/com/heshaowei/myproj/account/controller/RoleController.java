package com.heshaowei.myproj.account.controller;

import com.heshaowei.myproj.account.dto.RoleBaseDTO;
import com.heshaowei.myproj.account.dto.RoleDTO;
import com.heshaowei.myproj.account.entity.Role;
import com.heshaowei.myproj.account.repository.RoleRepository;
import com.heshaowei.myproj.account.service.RoleService;
import com.heshaowei.myproj.base.CurdController;
import com.heshaowei.myproj.bean.response.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/role")
public class RoleController extends CurdController<RoleRepository, Role, RoleBaseDTO> {


}
