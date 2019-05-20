package com.heshaowei.myproj.account.entity;

import com.heshaowei.myproj.account.entity.base.BaseEntity;
import lombok.Data;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import java.util.List;

@Entity
@Data
public class Resource extends BaseEntity {

    private String name;

    private String path;

    /**
     * 资源类型， view 视图、data 数据
     */
    private String type;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "resources")
    private List<Permission> permissions;
}
