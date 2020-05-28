package com.delivery.controller;

import com.delivery.dto.JwtResponse;
import com.delivery.dto.LoginRequest;
import com.delivery.dto.MessageResponse;
import com.delivery.dto.SignUpRequest;
import com.delivery.entity.Role;
import com.delivery.entity.User;
import com.delivery.exception.LoginIsBusyException;
import com.delivery.security.jwt.JwtUtils;
import com.delivery.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Collections;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {

	@Autowired
	private UserService userService;

	@Autowired
	private JwtUtils jwtUtils;

	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
		User user;
		try {
			user = userService.signIn(loginRequest.getUsername(), loginRequest.getPassword());
		} catch (BadCredentialsException ex) {
			return new ResponseEntity<>(ex.getMessage(), HttpStatus.FORBIDDEN);
		}

		return ResponseEntity.ok(
				new JwtResponse(
						jwtUtils.generateJwtToken(user),
						user.getId(),
						user.getLogin(),
						Collections.singletonList(user.getRole().name())
				)
		);
	}

	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {
		try {
			userService.signUp(
					signUpRequest.getUsername(),
					signUpRequest.getPassword(),
					signUpRequest.getUsername(),
					signUpRequest.getEmail(),
					signUpRequest.getUsername(),
					Role.CARGO_OWNER
			);
		} catch (LoginIsBusyException e) {
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Error: Login is busy!"));
		}

		return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
	}
}
