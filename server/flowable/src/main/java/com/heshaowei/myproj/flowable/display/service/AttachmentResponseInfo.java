package com.heshaowei.myproj.flowable.display.service;

import com.fasterxml.jackson.databind.JsonNode;

public class AttachmentResponseInfo extends ResponseInfo {
    protected String fileName;
    protected byte[] bytes;

    public AttachmentResponseInfo(String fileName, byte[] bytes) {
        super(200);
        this.fileName = fileName;
        this.bytes = bytes;
    }

    public AttachmentResponseInfo(int statusCode, JsonNode content) {
        super(statusCode, content);
    }

    public String getFileName() {
        return this.fileName;
    }

    public byte[] getBytes() {
        return this.bytes;
    }
}
