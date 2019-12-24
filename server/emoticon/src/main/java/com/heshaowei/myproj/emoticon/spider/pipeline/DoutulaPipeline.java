package com.heshaowei.myproj.emoticon.spider.pipeline;

import com.google.common.collect.Lists;
import com.heshaowei.myproj.emoticon.config.FilePaths;
import com.heshaowei.myproj.emoticon.entity.EmoticonCatalog;
import com.heshaowei.myproj.emoticon.entity.EmoticonImg;
import com.heshaowei.myproj.emoticon.repository.EmoticonCatalogRepository;
import com.heshaowei.myproj.emoticon.spider.DoutulaSpider;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.List;
import java.util.UUID;

@Service
@Log4j2
public class DoutulaPipeline implements Pipeline {

    @Autowired
    private EmoticonCatalogRepository emoticonCatalogRepository;

    @Autowired
    private FilePaths filePaths;

    @Autowired
    private DoutulaSpider doutulaSpider;

    @Override
    public void process(ResultItems resultItems, Task task) {
        EmoticonCatalog catalog = resultItems.get("catalog");

        System.out.println(catalog);
        if (null != catalog) {
            //判断是否存在相同类别
            boolean exist = this.doutulaSpider.exists(catalog.getCatalog());

            if (!exist) {
                List<EmoticonImg> imgs = Lists.newArrayList();
                for (EmoticonImg img : catalog.getImgs()) {
                    String src = img.getSrc();
                    String suffix = src.substring(src.lastIndexOf("."));
                    String path = File.separator + "doutula" + File.separator + catalog.getCatalog() + File.separator + UUID.randomUUID() + suffix;
                    try {
                        downloadPicture(src, filePaths.getSavePath().get("emoticon") + path);
                    } catch (Exception e) {
                        log.error(e.getMessage(), e);
                        continue;
                    }

                    img.setPath(path);
                    img.setCatalog(catalog);

                    imgs.add(img);
                }

                if (!imgs.isEmpty()) {
                    catalog.setImgs(imgs);
                    this.saveCatalog(catalog);
                }
            }
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public void saveCatalog(EmoticonCatalog catalog) {
        emoticonCatalogRepository.save(catalog);
    }

    //链接url下载图片
    private static void downloadPicture(String urlList, String savePath) throws Exception {
        URL url = new URL(urlList);
        URLConnection uc;
        uc = url.openConnection();
        uc.addRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/74.0.3729.157 Safari/537.36");
        uc.setDoInput(true);//设置是否要从 URL 连接读取数据,默认为true
        uc.connect();

        InputStream inputStream = uc.getInputStream();

        File file = new File(savePath);
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        ByteArrayOutputStream output = new ByteArrayOutputStream();

        byte[] buffer = new byte[1024];
        int length;

        while ((length = inputStream.read(buffer)) > 0) {
            output.write(buffer, 0, length);
        }
        fileOutputStream.write(output.toByteArray());
        inputStream.close();
        fileOutputStream.close();
    }

    public static void main(String[] args) throws Exception {
        downloadPicture("http://img.doutula.com/production/uploads/image//2019/05/21/20190521396727_OjDVRJ.gif", "F:\\picture\\project\\test.gif");
    }
}
