package com.heshaowei.myproj.emoticon.entity;

import com.heshaowei.myproj.emoticon.entity.base.BaseEntity;
import lombok.Data;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
@Data
public class EmoticonCatalog extends BaseEntity {
    /**
     * 类别名
     */
    @Column(unique = true)
    private String catalog;

    /**
     * 时间
     */
    private String time;

    /**
     * 图片
     */
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "catalog")
    private List<EmoticonImg> imgs;
}
