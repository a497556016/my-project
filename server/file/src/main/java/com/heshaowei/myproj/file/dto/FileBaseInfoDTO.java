package com.heshaowei.myproj.file.dto;

import com.google.common.base.Converter;
import com.heshaowei.myproj.file.entity.FileBaseInfo;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.beans.BeanUtils;

import java.util.Date;

@Accessors(chain = true)
@Data
@Builder
public class FileBaseInfoDTO extends Converter<FileBaseInfoDTO, FileBaseInfo> {

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
    private Date createTime;
    private String createUser;

    @Override
    protected FileBaseInfo doForward(FileBaseInfoDTO fileBaseInfoDTO) {
        FileBaseInfo fileBaseInfo = new FileBaseInfo();
        BeanUtils.copyProperties(fileBaseInfoDTO, fileBaseInfo);
        return fileBaseInfo;
    }

    @Override
    protected FileBaseInfoDTO doBackward(FileBaseInfo fileBaseInfo) {
        FileBaseInfoDTO fileBaseInfoDTO = FileBaseInfoDTO.builder().build();
        BeanUtils.copyProperties(fileBaseInfo, fileBaseInfoDTO);
        return fileBaseInfoDTO;
    }
}
