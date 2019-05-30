package com.heshaowei.myproj.emoticon.task;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
@Log4j2
public class MyAppRunner implements ApplicationRunner {

    @Autowired
    private DoutulaTask doutulaTask;

    @Override
    public void run(ApplicationArguments args) throws Exception {
//        log.info("开始启动表情图爬取任务......");
//        this.doutulaTask.execute();
    }
}
