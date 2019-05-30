package com.heshaowei.myproj.emoticon.spider.model;

import lombok.Data;
import us.codecraft.webmagic.model.annotation.ExtractBy;
import us.codecraft.webmagic.model.annotation.TargetUrl;

@TargetUrl("https://www.doutula.com/article/detail/\\d+")
@Data
public class DoutulaModel {

    @ExtractBy("//div[@class='pic-title']/h1/a/@href")
    private String catalog;

    @ExtractBy("//")
    private String title;

    private String src;

    private String time;
}
