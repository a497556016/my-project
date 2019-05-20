package com.heshaowei.myproj.file.controller;

import com.heshaowei.myproj.bean.response.Result;
import com.heshaowei.myproj.file.common.global.DownloadLog;
import com.heshaowei.myproj.file.props.FileProperties;
import org.apache.commons.codec.binary.StringUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

@RestController
@RequestMapping("/download")
public class DownloadController {

    @Autowired
    private FileProperties fileProperties;

    @GetMapping
    public void index(HttpServletResponse response, @RequestParam("path") String encodedPath) {
        DownloadLog.add();
        try {
            String path = URLDecoder.decode(encodedPath, StandardCharsets.UTF_8.name());
            File file = new File(fileProperties.getSavePath() + path);
            if (file.exists()) {
                try {
                    OutputStream outputStream = response.getOutputStream();
                    IOUtils.write(FileUtils.readFileToByteArray(file), outputStream);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}
