package com.heshaowei.myproj.auth.utils.token;

import lombok.Data;

@Data
public class TokenResponse {
    private String accessToken;
    private long expireTime;

    public TokenResponse(String accessToken, long expireTime) {
        this.accessToken = accessToken;
        this.expireTime = expireTime;
    }
}
