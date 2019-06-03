package com.heshaowei.myproj.emoticon.job;

public interface IJobHandler {
    void execute();

    void close();

    String status();
}
