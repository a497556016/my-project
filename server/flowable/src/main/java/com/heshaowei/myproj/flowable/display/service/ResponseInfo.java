//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.heshaowei.myproj.flowable.display.service;

import com.fasterxml.jackson.databind.JsonNode;

public class ResponseInfo {
    protected int statusCode;
    protected JsonNode content;

    public ResponseInfo(int statusCode) {
        this(statusCode, (JsonNode)null);
    }

    public ResponseInfo(int statusCode, JsonNode content) {
        this.statusCode = statusCode;
        this.content = content;
    }

    public int getStatusCode() {
        return this.statusCode;
    }

    public JsonNode getContent() {
        return this.content;
    }

    public boolean isSuccess() {
        return this.statusCode == 200;
    }
}
