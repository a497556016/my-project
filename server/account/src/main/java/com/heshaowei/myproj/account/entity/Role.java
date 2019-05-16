package com.heshaowei.myproj.account.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@Entity
public class Role extends BaseEntity implements Serializable {

    @NotNull
    private String name;

    @NotNull
    private String code;
}
