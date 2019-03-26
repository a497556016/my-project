package com.heshaowei.myproj.account.service;

import com.heshaowei.myproj.account.service.impl.FileServiceImpl;
import lombok.Data;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "file-server", fallback = FileServiceImpl.class)
public interface IFileService {

    @PutMapping("/file-base-info/updateBusId")
    boolean updateBusId(@RequestBody FileReq fileReq);

    @DeleteMapping("/file-base-info/delete")
    boolean delete(String path);

    @Data
    class FileReq{
        private String path;
        private String busId;

        public FileReq(String path, String busId) {
            this.path = path;
            this.busId = busId;
        }
    }
}
