package com.heshaowei.myproj.account.service;

import com.heshaowei.myproj.account.entity.Role;
import com.heshaowei.myproj.account.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class RoleService {
    @Autowired
    private RoleRepository roleRepository;

    public Page<Role> selectPage(PageRequest pr) {
        return this.roleRepository.findAll(pr);
    }
}
