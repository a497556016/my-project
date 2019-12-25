package com.heshaowei.myproj.flowable.bean;

import lombok.Data;

import java.io.Serializable;

@Data
public class TestProcessAssignUsers implements Serializable {
    private String user1;
    private String user2;

    public TestProcessAssignUsers(String user1, String user2) {
        this.user1 = user1;
        this.user2 = user2;
    }
}
