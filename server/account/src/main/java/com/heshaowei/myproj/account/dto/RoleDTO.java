package com.heshaowei.myproj.account.dto;

import com.google.common.base.Converter;
import com.heshaowei.myproj.account.entity.Role;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.beans.BeanUtils;

import java.util.Date;

@Data
@Accessors(chain = true)
@NoArgsConstructor
public class RoleDTO extends Converter<RoleDTO, Role> {

    private Long id;
    private String name;
    private String code;
    private String creater;
    private Date createTime;

    @Override
    protected Role doForward(RoleDTO roleDTO) {
        Role role = new Role();
        BeanUtils.copyProperties(roleDTO, role);
        return role;
    }

    @Override
    protected RoleDTO doBackward(Role role) {
        RoleDTO dto = new RoleDTO();
        BeanUtils.copyProperties(role, dto);
        return dto;
    }
}
