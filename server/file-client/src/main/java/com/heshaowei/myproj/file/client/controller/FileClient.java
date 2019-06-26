package com.heshaowei.myproj.file.client.controller;

import com.heshaowei.myproj.file.client.config.Global;
import com.heshaowei.myproj.file.client.controller.impl.FileClientImpl;
import com.heshaowei.myproj.file.client.domain.FileReq;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(serviceId = Global.APP_ID, fallback = FileClientImpl.class)
public interface FileClient {

    @PutMapping("/file-base-info/updateBusId")
    boolean updateBusId(@RequestBody FileReq fileReq);

    @DeleteMapping("/file-base-info/delete")
    boolean delete(String path);
}
