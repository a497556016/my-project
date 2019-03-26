package com.heshaowei.myproj.file.entity;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
public class FileBaseInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     * 业务ID
     */
    private String busId;
    private String name;
    /**
     * 文件大小
     */
    private Long size;
    private String contentType;
    private String path;
    private String thumbPath;
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;
    private String createUser;
    @Column(name="`delete`", columnDefinition = "bit(1) DEFAULT b'0'")
    private Boolean delete = false;
}
