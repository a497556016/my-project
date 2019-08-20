package com.heshaowei.myproj.auth.core;

import com.heshaowei.myproj.auth.core.model.ConfigAttribute;

import java.util.Collection;

public interface SecurityMetadataSourceService {
    /**
     * 在系统初始化的时候调用
     * 加载每个资源需要的角色映射关系
     */
    void loadResourceDefine();

    Collection<ConfigAttribute> getAttributes(String path);

}
