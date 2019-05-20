package com.heshaowei.myproj.file.controller;

import com.google.common.collect.Maps;
import com.heshaowei.myproj.bean.response.Result;
import com.heshaowei.myproj.file.common.global.DownloadLog;
import com.heshaowei.myproj.file.props.FileProperties;
import com.heshaowei.myproj.file.service.FileBaseInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.Map;

/**
 * 统计数据
 */
@RestController
@RequestMapping("/count")
public class CountController {

    @Autowired
    private FileProperties fileProperties;
    @Autowired
    private FileBaseInfoService fileBaseInfoService;

    /**
     * 分类统计首页数据
     *
     * @return
     */
    @GetMapping("/getClassifiedStatistical")
    public Result getClassifiedStatistical() {
        File file = new File(this.fileProperties.getSavePath());
        double usableSpace = (double) file.getUsableSpace() / 1024 / 1024 / 1024;
        DecimalFormat df = new DecimalFormat("#.###");
        String diskLeftSpace = df.format(usableSpace);

        long totalSize = this.fileBaseInfoService.selectTotalSize();
        String fileUsedSpace = df.format((double) totalSize / 1024 / 1024);

        long todayUploadTimes = this.fileBaseInfoService.selectUploadTimes(new Date());

        long size = this.fileBaseInfoService.selectUploadSize(new Date());
        String todayUploadSize = df.format((double) size / 1024 / 1024);

        Map<String, Object> result = Maps.newHashMap();
        result.put("diskLeftSpace", diskLeftSpace);
        result.put("fileUsedSpace", fileUsedSpace);
        result.put("todayDownloadTimes", DownloadLog.get());
        result.put("todayUploadSize", todayUploadSize);
        result.put("todayUploadTimes", todayUploadTimes);
        return Result.success(result);
    }
}
