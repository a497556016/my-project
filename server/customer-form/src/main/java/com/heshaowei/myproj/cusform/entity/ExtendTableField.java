package com.heshaowei.myproj.cusform.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * <p>
 *
 * </p>
 *
 * @author heshaowei
 * @since 2019-02-19
 */
@Data
@Entity
public class ExtendTableField implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @Id
    @GeneratedValue
    private Integer id;

    /**
     * 表ID
     */
    private Integer tableId;

    /**
     * 名称
     */
    private String name;

    /**
     * 字段类型
     */
    private String type;

    /**
     * 长度
     */
    private Integer length;

    /**
     * 是否允许为空
     */
    private Boolean nullable;

    /**
     * 默认值
     */
    private String defaultValue;

    /**
     * 字段描述
     */
    private String comment;


}
