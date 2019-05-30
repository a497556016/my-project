package com.heshaowei.myproj.emoticon.spider.processer;

import com.heshaowei.myproj.emoticon.entity.EmoticonCatalog;
import com.heshaowei.myproj.emoticon.entity.EmoticonImg;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.assertj.core.util.Lists;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.ConsolePipeline;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Selectable;

import java.util.List;

@RequiredArgsConstructor(staticName = "of")
public class DoutulaPageProcesser implements PageProcessor {

    @NonNull
    private String domain;

    private Site site = Site.me().setDomain(domain);

    @Override
    public void process(Page page) {
        List<String> detailUrls = page.getHtml().links().regex("https://www.doutula.com/article/detail/\\d+").all();
        //下一页
        String nextUrl = page.getHtml().xpath("//a[@class='page-link'][@rel='next']/@href").get();

        if(detailUrls.isEmpty()) {
            page.setSkip(true);
        }

        if(page.getRequest().getUrl().matches("https://www.doutula.com/article/detail/\\d+")) {
            String catalog = page.getHtml().xpath("//div[@class='pic-title']/h1/a/text()").get();
            String time = page.getHtml().xpath("//span[@class='glyphicon glyphicon-time']/text()").get();


            List<Selectable> imgs = page.getHtml().xpath("//div[@class='artile_des']/table/tbody/tr/td/a/img").nodes();

            EmoticonCatalog emoticonCatalog = new EmoticonCatalog();
            emoticonCatalog.setCatalog(catalog);
            emoticonCatalog.setTime(time);

            List<EmoticonImg> emoticonImgs = Lists.newArrayList();
            imgs.forEach(img -> {
                EmoticonImg ei = new EmoticonImg();
                ei.setTitle(img.xpath("//img/@alt").get());
                ei.setSrc(img.xpath("//img/@src").get());

                emoticonImgs.add(ei);
            });
            emoticonCatalog.setImgs(emoticonImgs);

            page.putField("catalog", emoticonCatalog);
        }else {
            page.addTargetRequests(detailUrls);
        }

        if(null != nextUrl) {
            page.addTargetRequest(nextUrl);
        }
    }

    @Override
    public Site getSite() {
        return site;
    }


}
