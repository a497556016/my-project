package com.heshaowei.myproj.file.bean;

import org.springframework.data.domain.Page;

public class PageResult<T> extends com.heshaowei.myproj.bean.response.PageResult<T> {
    public static <T> PageResult<T> success(Page<T> page){
        PageResult pr = new PageResult();
        pr.setData(page.getContent());
        pr.setTotal(page.getTotalElements());
        pr.setCurrent(page.getPageable().getPageNumber()+1);
        pr.setSize(page.getSize());
        pr.setCode(SUCCESS);
        return pr;
    }
}
