package com.heshaowei.myproj.emoticon.service;

import com.heshaowei.myproj.emoticon.dto.SpiderTaskDTO;
import com.heshaowei.myproj.emoticon.entity.SpiderTask;
import com.heshaowei.myproj.emoticon.repository.SpiderTaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class SpiderTaskService {
    @Autowired
    private SpiderTaskRepository spiderTaskRepository;

    public Page<SpiderTask> findByPage(int current, int size, Example<SpiderTask> example) {
        Page<SpiderTask> page = this.spiderTaskRepository.findAll(example, PageRequest.of(current-1, size));

        return page;
    }
}
