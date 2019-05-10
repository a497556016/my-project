package com.heshaowei.myproj.cusform.controller;

import com.google.common.collect.Lists;
import com.heshaowei.myproj.bean.response.PageResult;
import com.heshaowei.myproj.bean.response.Result;
import com.heshaowei.myproj.cusform.dto.CustomerFormDTO;
import com.heshaowei.myproj.cusform.entity.mongo.CustomerForm;
import com.heshaowei.myproj.cusform.service.CustomerFormService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@RestController
@RequestMapping("/customerForm")
@Log4j2
public class CustomerFormController {

    @Autowired
    private CustomerFormService formService;

    @PostMapping("/save")
    public Result save(@RequestBody CustomerForm form, HttpServletRequest request){
        String username = request.getHeader("username");
//        form.setCreateTime(new Date());
        form.setCreateBy(username);
        this.formService.save(form);
        return Result.success();
    }

    @GetMapping("/selectPage")
    public PageResult<CustomerFormDTO> selectPage(int current, int size){
        Page<CustomerForm> page = this.formService.selectPage(PageRequest.of(current-1, size));
        Iterable<CustomerFormDTO> iterable = CustomerFormDTO.builder().build().convertAll(page.getContent());
        return PageResult.of(CustomerFormDTO.class, current, size).success(Lists.newArrayList(iterable), page.getTotalElements());
    }
}
