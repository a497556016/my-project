package com.heshaowei.myproj.cusform.controller;

import com.heshaowei.myproj.bean.response.Result;
import com.heshaowei.myproj.cusform.entity.mongo.CustomerForm;
import com.heshaowei.myproj.cusform.service.CustomerFormService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/customerForm")
@Log4j2
public class CustomerFormController {

    @Autowired
    private CustomerFormService formService;

    @PostMapping("/save")
    public Result save(@RequestBody CustomerForm form){
        this.formService.save(form);
        return Result.success();
    }
}
