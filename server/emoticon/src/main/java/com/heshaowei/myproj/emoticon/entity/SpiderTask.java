package com.heshaowei.myproj.emoticon.entity;

import com.heshaowei.myproj.emoticon.entity.base.BaseEntity;
import lombok.Data;

import javax.persistence.Entity;

@Entity
@Data
public class SpiderTask extends BaseEntity {
    /**
     * 名称
     */
    private String name;
    /**
     * 任务实例
     */
    private String jobInstance;
    /**
     * 爬虫任务网址
     */
    private String url;
    /**
     * 任务执行规则
     */
    private String cron;
    /**
     * 负责人
     */
    private String owner;
}
