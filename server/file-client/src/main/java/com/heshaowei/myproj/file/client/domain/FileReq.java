package com.heshaowei.myproj.file.client.domain;

import lombok.Data;

@Data
public class FileReq {
    private String path;
    private String busId;

    public FileReq(String path, String busId) {
        this.path = path;
        this.busId = busId;
    }
}