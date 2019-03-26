package com.heshaowei.myproj.file.service;

import com.heshaowei.myproj.file.bean.PageReq;
import com.heshaowei.myproj.file.entity.FileBaseInfo;
import com.heshaowei.myproj.file.repository.FileBaseInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class FileBaseInfoService {

    @Autowired
    private FileBaseInfoRepository fileBaseInfoRepository;

    public Page<FileBaseInfo> findByPage(PageReq pageReq) {

        return this.fileBaseInfoRepository.findAll(pageReq.turn());
    }

    public boolean updateBusId(String path, String busId) {
        FileBaseInfo probe = new FileBaseInfo();
        probe.setPath(path);
        FileBaseInfo fbi = this.fileBaseInfoRepository.findOne(Example.of(probe)).orElse(null);
        if(null != fbi) {
            fbi.setBusId(busId);
            this.fileBaseInfoRepository.saveAndFlush(fbi);
        }
        return true;
    }

    public boolean delete(String path) {
        FileBaseInfo probe = new FileBaseInfo();
        probe.setPath(path);
        FileBaseInfo fbi = this.fileBaseInfoRepository.findOne(Example.of(probe)).orElse(null);
        if(null != fbi) {
            fbi.setDelete(true);
            this.fileBaseInfoRepository.saveAndFlush(fbi);
        }
        return true;
    }
}
