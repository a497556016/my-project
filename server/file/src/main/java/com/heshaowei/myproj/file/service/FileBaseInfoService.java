package com.heshaowei.myproj.file.service;

import com.heshaowei.myproj.file.bean.PageReq;
import com.heshaowei.myproj.file.entity.FileBaseInfo;
import com.heshaowei.myproj.file.repository.FileBaseInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class FileBaseInfoService {

    @Autowired
    private FileBaseInfoRepository fileBaseInfoRepository;

    public Page<FileBaseInfo> findByPage(PageReq pageReq) {

        return this.fileBaseInfoRepository.findAll(pageReq.turn());
    }
}
