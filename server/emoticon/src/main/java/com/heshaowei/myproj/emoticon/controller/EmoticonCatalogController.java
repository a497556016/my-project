package com.heshaowei.myproj.emoticon.controller;

import com.heshaowei.myproj.base.CurdController;
import com.heshaowei.myproj.emoticon.dto.EmoticonCatalogDTO;
import com.heshaowei.myproj.emoticon.entity.EmoticonCatalog;
import com.heshaowei.myproj.emoticon.repository.EmoticonCatalogRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/emoticonCatalog")
public class EmoticonCatalogController extends CurdController<EmoticonCatalogRepository, EmoticonCatalog, EmoticonCatalogDTO> {

}
