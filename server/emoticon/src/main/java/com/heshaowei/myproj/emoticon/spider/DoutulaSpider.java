package com.heshaowei.myproj.emoticon.spider;

import com.heshaowei.myproj.emoticon.repository.EmoticonCatalogRepository;
import com.heshaowei.myproj.emoticon.spider.pipeline.DoutulaPipeline;
import com.heshaowei.myproj.emoticon.spider.processer.DoutulaPageProcesser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Spider;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class DoutulaSpider {
    private static final String doMain = "www.doutula.com";
    private static final String url = "https://www.doutula.com/";

    @Autowired
    private DoutulaPipeline pipeline;
    @Autowired
    private EmoticonCatalogRepository emoticonCatalogRepository;

    private List<String> catalogs;

    private Spider spider;

    private void setCatalogs() {
        if (null == catalogs) {
            catalogs = this.emoticonCatalogRepository.findAll().stream().map(c -> c.getCatalog()).collect(Collectors.toList());
        }
    }

    public boolean exists(String catalog) {
        return this.catalogs.contains(catalog);
    }

    public void execute() {
        if (null == this.spider) {
            this.spider = this.createSpider();
        }
//        if(this.spider.getStatus().equals(Spider.Status.Stopped)){
//            this.spider.start();
//        }
//        else if(!this.spider.getStatus().equals(Spider.Status.Running)) {
        this.spider.thread(8).run();
//        }
    }

    public void close() {
        if (null != this.spider) {
            this.spider.stop();
            this.spider.close();
            this.spider = null;
        }
    }

    public String status() {
        if (null == this.spider) {
            return null;
        }
        Spider.Status status = this.spider.getStatus();
        return status.name();
    }

    private Spider createSpider() {
        this.setCatalogs();
        return Spider.create(DoutulaPageProcesser.of(doMain))
                .addUrl(url)
                //内容输出
                .addPipeline(pipeline);
    }
}
