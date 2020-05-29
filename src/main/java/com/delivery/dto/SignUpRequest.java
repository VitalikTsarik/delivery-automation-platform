package com.delivery.dto;

import com.delivery.entity.Role;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class SignUpRequest {
    @NotBlank
    @Size(min = 3, max = 20)
    private String login;
    
    private Role role;
    
    @NotBlank
    @Size(min = 6, max = 40)
    private String password;
}
