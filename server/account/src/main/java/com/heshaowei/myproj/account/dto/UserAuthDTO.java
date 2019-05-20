package com.heshaowei.myproj.account.dto;

import com.heshaowei.myproj.account.entity.User;
import com.heshaowei.myproj.base.MyConverter;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@NoArgsConstructor
public class UserAuthDTO extends MyConverter<UserAuthDTO, User> {
    private String username;
    private String password;
}
