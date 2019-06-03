package com.heshaowei.myproj.emoticon.controller;

import com.heshaowei.myproj.base.CurdController;
import com.heshaowei.myproj.emoticon.config.FilePaths;
import com.heshaowei.myproj.emoticon.dto.EmoticonImgDTO;
import com.heshaowei.myproj.emoticon.entity.EmoticonImg;
import com.heshaowei.myproj.emoticon.repository.EmoticonImgRepository;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.websocket.server.PathParam;
import java.io.File;
import java.util.UUID;

@RestController
@RequestMapping("/emoticonImg")
public class EmoticonImgController extends CurdController<EmoticonImgRepository, EmoticonImg, EmoticonImgDTO> {

    @Autowired
    private FilePaths filePaths;

    @GetMapping("/download")
    public void download(String path, HttpServletResponse response){
        System.out.println(path);
        try {
            byte[] bus = FileUtils.readFileToByteArray(new File(filePaths.getSavePath().get("emoticon") + File.separator + path));
            IOUtils.write(bus, response.getOutputStream());
            response.setContentType("application/image");
            response.addHeader("Content-Disposition", "attachment; filename="+ UUID.randomUUID()+path.substring(path.lastIndexOf(".")));
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
