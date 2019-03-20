package com.heshaowei.myproj.file.controller;

import com.heshaowei.myproj.file.bean.PageReq;
import com.heshaowei.myproj.file.bean.PageResult;
import com.heshaowei.myproj.file.entity.FileBaseInfo;
import com.heshaowei.myproj.file.service.FileBaseInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/file-base-info")
public class FileBaseInfoController {

    @Autowired
    private FileBaseInfoService fileBaseInfoService;

    @GetMapping("/findByPage")
    public PageResult findByPage(PageReq pageReq){
        pageReq.setSort(Sort.by(Sort.Direction.DESC, "createTime"));
        Page<FileBaseInfo> page = this.fileBaseInfoService.findByPage(pageReq);
        return PageResult.success(page);
    }
}
