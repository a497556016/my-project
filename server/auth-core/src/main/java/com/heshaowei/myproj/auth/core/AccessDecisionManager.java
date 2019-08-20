package com.heshaowei.myproj.auth.core;

import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.heshaowei.myproj.auth.core.model.ConfigAttribute;
import com.heshaowei.myproj.auth.core.model.RoleAttribute;
import com.heshaowei.myproj.auth.core.model.UserInfo;
import com.heshaowei.myproj.auth.utils.token.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;

/**
 * 权限验证
 */
@Component
public class AccessDecisionManager {

    @Autowired
    private SecurityMetadataSourceService securityMetadataSourceService;

    /**
     * 验证用户是否有访问路径path的权限
     * @param userInfo
     * @param path
     * @return
     */
    public boolean verify(UserInfo userInfo, String path){

        List<RoleAttribute> roleAttributes = userInfo.getRoleAttributes();

        //需求的权限
        Collection<ConfigAttribute> list = this.securityMetadataSourceService.getAttributes(path);
        if(null == list) {
            return true;//不需要任何权限即可访问
        }
        //判断如果用户拥有权限的资源在需求的资料列表内，则有权限访问
        for (ConfigAttribute configAttribute : list) {
            boolean match = roleAttributes.stream().anyMatch(roleAttribute -> roleAttribute.getAttribute().equals(configAttribute.getAttribute()));
            if(match){
                return true;
            }
        }

        return false;
    }

    public boolean verify(String accessToken, String path) {
        String userinfoJson = JWTUtil.getUserinfo(accessToken);
        Gson gson = new Gson();
        UserInfo userInfo = gson.fromJson(userinfoJson, UserInfo.class);

        try {
            JWTUtil.verify(accessToken, userinfoJson, userInfo.getPassword());
        }catch (Exception e) {
            throw new SecurityException("验证token权限失败！");
        }

        return this.verify(userInfo, path);
    }
}
