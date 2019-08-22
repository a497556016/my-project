package com.heshaowei.myproj.account.dto;

import com.heshaowei.myproj.account.entity.Resource;
import com.heshaowei.myproj.base.MyConverter;
import lombok.Data;

import java.util.Date;

@Data
public class ResourceDTO<T extends MyConverter> extends MyConverter<T, Resource> {
    private Long id;

    private String name;

    private String path;

    /**
     * 资源类型， view 视图、data 数据
     */
    private String type;

    private String creater;

    private Date createTime;
}
