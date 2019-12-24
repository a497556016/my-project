package com.heshaowei.myproj.emoticon.task;

import com.heshaowei.myproj.emoticon.job.IJobHandler;
import com.heshaowei.myproj.emoticon.spider.DoutulaSpider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Spider;

/**
 * 爬取 https://www.doutula.com/ 上的表情任务
 */
@Component("doutulaTask")
public class DoutulaTask implements IJobHandler {

    @Autowired
    private DoutulaSpider doutulaSpider;

    //    @Scheduled(cron = "0 0 2 ? * *")
    @Override
    public void execute() {
        this.doutulaSpider.execute();
    }

    @Override
    public void close() {
        this.doutulaSpider.close();
    }

    @Override
    public String status() {
        return this.doutulaSpider.status();
    }
}
