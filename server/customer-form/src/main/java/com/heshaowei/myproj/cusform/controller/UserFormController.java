package com.heshaowei.myproj.cusform.controller;

import com.google.common.collect.Lists;
import com.heshaowei.myproj.bean.response.Result;
import com.heshaowei.myproj.cusform.dto.CustomerFormDTO;
import com.heshaowei.myproj.cusform.dto.CustomerFormItemDTO;
import com.heshaowei.myproj.cusform.entity.mongo.CustomerForm;
import com.heshaowei.myproj.cusform.entity.mongo.CustomerFormItem;
import com.heshaowei.myproj.cusform.entity.mongo.UserForm;
import com.heshaowei.myproj.cusform.service.CustomerFormService;
import com.heshaowei.myproj.cusform.service.UserFormService;
import com.netflix.discovery.converters.Auto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@RestController
@RequestMapping("/userForm")
public class UserFormController {
    @Autowired
    private CustomerFormService formService;
    @Autowired
    private UserFormService userFormService;

    @GetMapping("/selectById")
    public Result<CustomerFormDTO> selectById(String id) {
        CustomerForm form = this.formService.selectById(id);
        return Result.success(CustomerFormDTO.builder().build().convert(form));
    }

    @PostMapping("/save")
    public Result<?> save(@RequestBody CustomerFormDTO dto, HttpServletRequest request) {
        String username = request.getHeader("username");
        Iterable<CustomerFormItem> items = CustomerFormItemDTO.builder().build().reverse().convertAll(dto.getFormItems());
        UserForm userForm = new UserForm()
                .setFormId(dto.getId())
                .setUsername(username)
                .setItems(Lists.newArrayList(items))
                .setCreateTime(new Date());

        this.userFormService.save(userForm);
        return Result.success();
    }
}
