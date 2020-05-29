package com.delivery.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class LoginRequest {
	@NotBlank(message = "Username shouldn't be blank")
	private String username;

	@NotBlank(message = "Password shouldn't be blank")
	private String password;
}
