package com.heshaowei.myproj.emoticon.dto;

import com.heshaowei.myproj.base.MyConverter;
import com.heshaowei.myproj.emoticon.entity.SpiderTask;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class SpiderTaskDTO extends MyConverter<SpiderTaskDTO, SpiderTask> {
    /**
     * ID
     */
    private Long id;
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

    /**
     * 定时任务状态
     */
    private String status;

    /**
     * 执行任务状态
     */
    private String runStatus;
}
