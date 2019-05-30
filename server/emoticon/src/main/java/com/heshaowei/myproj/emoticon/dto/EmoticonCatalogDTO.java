package com.heshaowei.myproj.emoticon.dto;

import com.heshaowei.myproj.base.MyConverter;
import com.heshaowei.myproj.emoticon.entity.EmoticonCatalog;
import lombok.Data;

@Data
public class EmoticonCatalogDTO extends MyConverter<EmoticonCatalogDTO, EmoticonCatalog> {

    private Long id;

    private String catalog;

    private String time;

}
