package com.heshaowei.myproj.file.controller;

import com.heshaowei.myproj.bean.response.Result;
import com.heshaowei.myproj.file.entity.FileBaseInfo;
import com.heshaowei.myproj.file.props.FileProperties;
import com.heshaowei.myproj.file.repository.FileBaseInfoRepository;
import com.heshaowei.myproj.utils.image.ImageHandler;
import com.heshaowei.myproj.utils.image.ImageUtils;
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

    private void saveMultipartFile(MultipartFile file, FileBaseInfo fileBaseInfo, String username) {

        //创建保存相对路径
        String relativePath = File.separator + DateFormatUtils.format(new Date(), "yyyyMMdd");

        String suffix = ".jpg";
        String filename = file.getOriginalFilename();
        int i = filename.lastIndexOf(".");
        if (i >= 0) {
            suffix = filename.substring(i);
        }
        String fileSaveName = UUID.randomUUID().toString();
        String fileSavePath = File.separator + fileSaveName + suffix;

        String folderPath = fileProperties.getSavePath() + relativePath;
        File saveFile = new File(folderPath + fileSavePath);
        try {
            fileBaseInfo.setName(filename);
            fileBaseInfo.setPath(relativePath + fileSavePath);
            fileBaseInfo.setContentType(file.getContentType());
            fileBaseInfo.setCreateTime(new Date());
            fileBaseInfo.setCreateUser(username);
            fileBaseInfo.setSize(file.getSize());

            if (!saveFile.getParentFile().exists()) {
                saveFile.getParentFile().mkdirs();
            }

            //保存图片
            file.transferTo(saveFile);

            //对于图片类型，生成缩略图
            if (file.getContentType().startsWith("image")) {
                String thumbName = fileSaveName + "_thumb";
                new ImageHandler(saveFile.getAbsolutePath(), folderPath, thumbName, suffix.substring(1)).zoom(100, 100).writeToFile();
                fileBaseInfo.setThumbPath(relativePath + File.separator + thumbName + suffix);
            }

            fileBaseInfoRepository.save(fileBaseInfo);
        } catch (Exception e) {
            log.error(e);
            throw new RuntimeException("文件保存发生异常！");
        }

    }

    @PostMapping
    @Transactional(rollbackFor = Exception.class)
    public Result index(HttpServletRequest request) {

        final String username = Objects.toString(request.getHeader("username"), "");
        final FileBaseInfo fileBaseInfo = new FileBaseInfo();
        MultiValueMap<String, MultipartFile> filesMap = ((MultipartHttpServletRequest) request).getMultiFileMap();
        filesMap.forEach((name, files) -> files.forEach(file -> {
            saveMultipartFile(file, fileBaseInfo, username);
        }));
        return Result.success(fileBaseInfo);
    }

    public static void main(String[] args) {
        File file = new File("f://picture/project");
        System.out.println((double) file.getTotalSpace() / 1024 / 1024 / 1024);
        System.out.println((double) file.getUsableSpace() / 1024 / 1024 / 1024);
        System.out.println((double) file.length());
    }
}
