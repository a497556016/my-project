package com.heshaowei.myproj.account.controller;

import com.heshaowei.myproj.account.dto.ResourceBaseDTO;
import com.heshaowei.myproj.account.entity.Resource;
import com.heshaowei.myproj.account.repository.ResourceRepository;
import com.heshaowei.myproj.base.CurdController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/resource")
public class ResourceController extends CurdController<ResourceRepository, Resource, ResourceBaseDTO> {
}
