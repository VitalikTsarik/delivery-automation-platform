package com.delivery.dto;

import com.delivery.entity.Role;
import com.delivery.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserDto {
    private long id;
    private String login;
    private String firstName;
    private String secondName;
    private String patronymic;
    private Role role;

    public static UserDto build(User user) {
        return new UserDto(
                user.getId(),
                user.getLogin(),
                user.getFirstName(),
                user.getSecondName(),
                user.getPatronymic(),
                user.getRole()
        );
    }
}
