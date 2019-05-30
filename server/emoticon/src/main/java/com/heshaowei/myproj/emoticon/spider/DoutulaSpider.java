package com.heshaowei.myproj.emoticon.spider;

import com.heshaowei.myproj.emoticon.spider.pipeline.DoutulaPipeline;
import com.heshaowei.myproj.emoticon.spider.processer.DoutulaPageProcesser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Spider;

@Component
public class DoutulaSpider {
    private static final String doMain = "www.doutula.com";
    private static final String url = "https://www.doutula.com/";

    @Autowired
    private DoutulaPipeline pipeline;

    public Spider createSpider(){
        return Spider.create(DoutulaPageProcesser.of(doMain))
                .addUrl(url)
                //内容输出
                .addPipeline(pipeline);
    }
}
