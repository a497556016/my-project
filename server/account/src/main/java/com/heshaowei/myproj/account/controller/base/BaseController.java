package com.heshaowei.myproj.account.controller.base;

import com.google.common.base.Converter;
import com.google.common.collect.Lists;
import com.heshaowei.myproj.bean.response.PageResult;
import org.springframework.data.domain.Page;

import java.util.List;

public class BaseController {
    protected <E,T extends Converter<T, E>> PageResult<T> pageConvert(T t, Page<E> page){
        List list = Lists.newArrayList(t.reverse().convertAll(page.getContent()));
        return PageResult.of(t.getClass(), page.getNumber()+1, page.getSize()).success(list, page.getTotalElements());
    }
}
