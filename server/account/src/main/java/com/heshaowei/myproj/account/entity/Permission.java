package com.heshaowei.myproj.account.entity;

import com.heshaowei.myproj.account.entity.base.BaseEntity;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
public class Permission extends BaseEntity {

    private String name;

    private String code;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "permissions")
    private List<Role> roles;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Resource> resources;
}
