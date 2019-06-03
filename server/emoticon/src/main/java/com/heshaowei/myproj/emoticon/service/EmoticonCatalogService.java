package com.heshaowei.myproj.emoticon.service;

import com.heshaowei.myproj.emoticon.repository.EmoticonCatalogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmoticonCatalogService {
    @Autowired
    private EmoticonCatalogRepository emoticonCatalogRepository;

    private List<String> catalogs;

    public boolean exists(String catalog){
        if(null == catalogs) {
            catalogs = this.emoticonCatalogRepository.findAll().stream().map(c -> c.getCatalog()).collect(Collectors.toList());
        }

        return catalogs.contains(catalog);
    }
}
