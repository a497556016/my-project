package com.heshaowei.myproj.file.controller;

import com.heshaowei.myproj.bean.response.Result;
import com.heshaowei.myproj.file.entity.FileBaseInfo;
import com.heshaowei.myproj.file.props.FileProperties;
import com.heshaowei.myproj.file.repository.FileBaseInfoRepository;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@RestController
@RequestMapping("/upload")
@Log4j2
public class UploadController {

    @Autowired
    private FileProperties fileProperties;
    @Autowired
    private FileBaseInfoRepository fileBaseInfoRepository;

    private void saveMultipartFile(MultipartFile file, FileBaseInfo fileBaseInfo, String username){

        //创建保存相对路径
        String relativePath = File.separator + DateFormatUtils.format(new Date(), "yyyyMMdd");

        String suffix = ".jpg";
        String filename = file.getOriginalFilename();
        int i = filename.lastIndexOf(".");
        if(i>=0) {
            suffix = filename.substring(i);
        }
        String fileSaveName = File.separator + UUID.randomUUID().toString() + suffix;

        String folderPath = fileProperties.getSavePath() + relativePath;
        File saveFile = new File( folderPath + fileSaveName);
        try {
            fileBaseInfo.setName(filename);
            fileBaseInfo.setPath(relativePath + fileSaveName);
            fileBaseInfo.setContentType(file.getContentType());
            fileBaseInfo.setCreateTime(new Date());
            fileBaseInfo.setCreateUser(username);
            fileBaseInfoRepository.save(fileBaseInfo);

            if(!saveFile.getParentFile().exists()) {
                saveFile.getParentFile().mkdirs();
            }

            file.transferTo(saveFile);
        } catch (IOException e) {
            log.error(e);
            throw new RuntimeException("文件保存发生异常！");
        }

    }

    @PostMapping
    @Transactional(rollbackFor = Exception.class)
    public Result index(HttpServletRequest request){

        final String username = Objects.toString(request.getHeader("username"), "");
        final FileBaseInfo fileBaseInfo = new FileBaseInfo();
        MultiValueMap<String, MultipartFile> filesMap = ((MultipartHttpServletRequest)request).getMultiFileMap();
        filesMap.forEach((name, files) -> files.forEach(file -> {
            saveMultipartFile(file, fileBaseInfo, username);
        }));
        return Result.success(fileBaseInfo);
    }
}
