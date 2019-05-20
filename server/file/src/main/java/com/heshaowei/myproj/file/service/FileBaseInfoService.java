package com.heshaowei.myproj.file.service;

import com.heshaowei.myproj.file.entity.FileBaseInfo;
import com.heshaowei.myproj.file.repository.FileBaseInfoRepository;
import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class FileBaseInfoService {

    @Autowired
    private FileBaseInfoRepository fileBaseInfoRepository;

    public Page<FileBaseInfo> findByPage(PageRequest pr) {

        return this.fileBaseInfoRepository.findAll(pr);
    }

    public boolean updateBusId(String path, String busId) {
        FileBaseInfo probe = new FileBaseInfo();
        probe.setPath(path);
        FileBaseInfo fbi = this.fileBaseInfoRepository.findOne(Example.of(probe)).orElse(null);
        if (null != fbi) {
            fbi.setBusId(busId);
            this.fileBaseInfoRepository.saveAndFlush(fbi);
        }
        return true;
    }

    public boolean delete(String path) {
        FileBaseInfo probe = new FileBaseInfo();
        probe.setPath(path);
        FileBaseInfo fbi = this.fileBaseInfoRepository.findOne(Example.of(probe)).orElse(null);
        if (null != fbi) {
            fbi.setDelete(true);
            this.fileBaseInfoRepository.saveAndFlush(fbi);
        }
        return true;
    }

    public long selectTotalSize() {
        return this.fileBaseInfoRepository.selectTotalSize();
    }

    public long selectUploadTimes(Date date) {
        Date endDate = DateUtils.addDays(date, 1);
        return this.fileBaseInfoRepository.countByCreateTimeBetween(date, endDate);
    }

    @Transactional(rollbackFor = Exception.class)
    public long selectUploadSize(Date date) {
        Date endDate = DateUtils.addDays(date, 1);
        return this.fileBaseInfoRepository.streamAllByCreateTimeBetween(date, endDate).mapToLong(FileBaseInfo::getSize).sum();
    }
}
