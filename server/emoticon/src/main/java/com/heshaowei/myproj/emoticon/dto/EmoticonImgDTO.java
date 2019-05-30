package com.heshaowei.myproj.emoticon.dto;

import com.heshaowei.myproj.base.MyConverter;
import com.heshaowei.myproj.emoticon.entity.EmoticonCatalog;
import com.heshaowei.myproj.emoticon.entity.EmoticonImg;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@Accessors(chain = true)
public class EmoticonImgDTO extends MyConverter<EmoticonImgDTO, EmoticonImg> {

    /**
     * 图片名称
     */
    private String title;

    /**
     * 图片分类
     */
    private EmoticonCatalogDTO catalog;

    /**
     * 图片本地相对路径
     */
    private String path;

    private String src;

    /**
     * 图片详细描述
     */
    private String remark;

    @Override
    protected EmoticonImgDTO doBackward(EmoticonImg emoticonImg) {
        EmoticonImgDTO dto = super.doBackward(emoticonImg);

//        dto.setCatalog(new EmoticonCatalogDTO().reverse().convert(emoticonImg.getCatalog()));

        return dto;
    }

    @Override
    protected EmoticonImg doForward(EmoticonImgDTO emoticonImgDTO) {
        EmoticonImg img = super.doForward(emoticonImgDTO);

//        EmoticonCatalog catalog = new EmoticonCatalogDTO().convert(emoticonImgDTO.getCatalog());
//        img.setCatalog(catalog);

        return img;
    }
}
