package com.heshaowei.myproj.emoticon.task;

import com.heshaowei.myproj.emoticon.spider.DoutulaSpider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 爬取 https://www.doutula.com/ 上的表情任务
 */
@Component
public class DoutulaTask {

    @Autowired
    private DoutulaSpider spider;

    public static void main(String[] args) {
        new DoutulaTask().execute();
    }

    @Scheduled(cron = "0 0 2 ? * *")
    public void execute(){
        this.spider.createSpider().thread(8).run();
    }
}
