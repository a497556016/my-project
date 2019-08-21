package com.heshaowei.myproj.account.dto;

import com.heshaowei.myproj.account.entity.Permission;
import com.heshaowei.myproj.base.MyConverter;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@Accessors(chain = true)
@NoArgsConstructor
public class PermissionDTO<T extends MyConverter> extends MyConverter<T, Permission> {
    private Long id;

    private String name;

    private String code;

    private String creater;

    private Date createTime;
}
