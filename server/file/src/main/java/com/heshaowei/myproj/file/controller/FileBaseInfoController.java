package com.heshaowei.myproj.file.controller;

import com.heshaowei.myproj.bean.response.Result;
import com.heshaowei.myproj.file.bean.PageReq;
import com.heshaowei.myproj.file.bean.PageResult;
import com.heshaowei.myproj.file.entity.FileBaseInfo;
import com.heshaowei.myproj.file.service.FileBaseInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.io.File;

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

    @PutMapping("/updateBusId")
    public boolean updateBusId(@RequestBody FileBaseInfo fbi){
        String path = fbi.getPath();
        String busId = fbi.getBusId();
        if(null == busId) {
            return false;
        }
        //busId的格式必须为[serviceId：服务ID]/[业务表类型]/[业务数据ID]
        if(busId.contains("/")&&(busId.split("/").length==3)){
            return this.fileBaseInfoService.updateBusId(path, busId);
        }

        return false;
    }

    @DeleteMapping("/delete")
    public boolean delete(String path){
        return this.fileBaseInfoService.delete(path);
    }
}
