package com.heshaowei.myproj.im.server.enums;

public enum MessageTypes {
    USER("user"),
    GROUP("group");

    private String type;

    MessageTypes(String type){
        this.type = type;
    }

    public String getValue(){
        return this.type;
    }
}
