package com.heshaowei.myproj.auth.core.model;

public class RoleAttribute implements ConfigAttribute {
    private String roleName;

    public RoleAttribute(String name) {
        this.roleName = name;
    }

    @Override
    public String getAttribute() {
        return this.roleName;
    }
}
