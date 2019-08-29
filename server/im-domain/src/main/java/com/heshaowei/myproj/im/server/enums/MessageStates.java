package com.heshaowei.myproj.im.server.enums;

public enum MessageStates {
    //发送成功
    SUCCESS(1),
    //发送取消
    CANCEL(2),
    //未接收
    NOT_RECEIVE(3),
    //失败
    FAIL(4);
    private int state;

    MessageStates(int state){
        this.state = state;
    }
}
