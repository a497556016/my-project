package com.heshaowei.myproj.flowable.bean;

import lombok.Data;

import java.util.List;

@Data
public class ChangeStateParams {
    private List<String> startActivityIds;
    private List<String> cancelActivityIds;

    public boolean isNotEmpty() {
        if(null == startActivityIds || startActivityIds.isEmpty() || null == cancelActivityIds || cancelActivityIds.isEmpty()) {
            return false;
        }
        return true;
    }
}
