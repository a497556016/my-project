package com.heshaowei.myproj.file.client.controller.impl;

import com.heshaowei.myproj.file.client.controller.FileClient;
import com.heshaowei.myproj.file.client.domain.FileReq;
import org.springframework.stereotype.Component;

@Component
public class FileClientImpl implements FileClient {
    @Override
    public boolean updateBusId(FileReq fileReq) {
        return false;
    }

    @Override
    public boolean delete(String path) {
        return false;
    }
}
