package com.heshaowei.myproj.emoticon.service;

import com.heshaowei.myproj.emoticon.repository.EmoticonImgRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmoticonImgService {

    @Autowired
    private EmoticonImgRepository emoticonImgRepository;
}
