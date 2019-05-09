package com.heshaowei.myproj.cusform.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 测试扩展业务表
 * </p>
 *
 * @author heshaowei
 * @since 2019-02-15
 */
@Data
@Entity
public class ExtendTable implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @Id
    @GeneratedValue
    private Integer id;

    private String moduleName;

    private String tableName;

    private String tableDesc;

    private String creater;

    private Date createTime;

    private String modifier;

    private Date modifyTime;


}
