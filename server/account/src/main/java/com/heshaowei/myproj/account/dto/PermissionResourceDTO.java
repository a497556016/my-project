package com.heshaowei.myproj.account.dto;

import lombok.Data;

import java.util.List;

@Data
public class PermissionResourceDTO extends PermissionDTO<PermissionResourceDTO> {
    private List<ResourceDTO> resources;
}
