package com.heshaowei.myproj.account.dto;

import com.heshaowei.myproj.account.entity.User;
import lombok.*;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Data
@NoArgsConstructor
public class UserBaseDTO extends UserDTO<UserBaseDTO> {


    public User convert() {
        return convert(this);
    }
}
