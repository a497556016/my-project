package com.heshaowei.myproj.emoticon.entity;

import com.heshaowei.myproj.emoticon.entity.base.BaseEntity;
import lombok.Data;
import lombok.ToString;
import org.checkerframework.checker.units.qual.C;

import javax.persistence.*;

@Entity
@Data
@ToString
public class EmoticonImg extends BaseEntity {

    /**
     * 图片名称
     */
    @Column(length = 256)
    private String title;

    /**
     * 图片本地相对路径
     */
    @Column(length = 1024)
    private String path;

    /**
     * 原始链接
     */
    private String src;

    /**
     * 图片详细描述
     */
    @Column(length = 1024)
    private String remark;

    /**
     * 类别
     */
    @ManyToOne(cascade = CascadeType.ALL)
    private EmoticonCatalog catalog;

}
