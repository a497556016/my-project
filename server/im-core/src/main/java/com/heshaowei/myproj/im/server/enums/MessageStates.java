package com.heshaowei.myproj.im.server.enums;

public enum MessageStates {
    //发送成功
    SUCCESS(1),
    //发送失败
    FAIL(2),
    //未发送
    NOT_SEND(3);
    private int state;

    MessageStates(int state){
        this.state = state;
    }
}
