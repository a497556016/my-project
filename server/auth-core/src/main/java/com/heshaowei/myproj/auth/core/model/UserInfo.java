package com.heshaowei.myproj.auth.core.model;

import java.util.List;

public class UserInfo {
    private String username;
    private String password;
    private Boolean lock;

    private List<RoleAttribute> roleAttributes;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getLock() {
        return lock;
    }

    public void setLock(Boolean lock) {
        this.lock = lock;
    }

    public List<RoleAttribute> getRoleAttributes() {
        return roleAttributes;
    }

    public void setRoleAttributes(List<RoleAttribute> roleAttributes) {
        this.roleAttributes = roleAttributes;
    }
}
