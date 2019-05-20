package com.heshaowei.myproj.file.controller;

import com.google.common.collect.Lists;
import com.heshaowei.myproj.bean.response.PageResult;
import com.heshaowei.myproj.file.dto.FileBaseInfoDTO;
import com.heshaowei.myproj.file.entity.FileBaseInfo;
import com.heshaowei.myproj.file.service.FileBaseInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/file-base-info")
public class FileBaseInfoController {

    @Autowired
    private FileBaseInfoService fileBaseInfoService;

    @GetMapping("/selectPage")
    public PageResult<FileBaseInfoDTO> selectPage(int current, int size) {
        Page<FileBaseInfo> page = this.fileBaseInfoService.findByPage(PageRequest.of(current - 1, size, Sort.by(Sort.Direction.DESC, "createTime")));
        Iterable<FileBaseInfoDTO> records = FileBaseInfoDTO.builder().build().reverse().convertAll(page.getContent());
        return PageResult.of(FileBaseInfoDTO.class, current, size).success(Lists.newArrayList(records), page.getTotalElements());
    }

    @PutMapping("/updateBusId")
    public boolean updateBusId(@RequestBody FileBaseInfo fbi) {
        String path = fbi.getPath();
        String busId = fbi.getBusId();
        if (null == busId) {
            return false;
        }
        //busId的格式必须为[serviceId：服务ID]/[业务表类型]/[业务数据ID]
        if (busId.contains("/") && (busId.split("/").length == 3)) {
            return this.fileBaseInfoService.updateBusId(path, busId);
        }

        return false;
    }

    @DeleteMapping("/delete")
    public boolean delete(String path) {
        return this.fileBaseInfoService.delete(path);
    }
}
