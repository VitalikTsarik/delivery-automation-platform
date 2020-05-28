package com.delivery.controller;

import com.delivery.dto.JwtResponse;
import com.delivery.dto.LoginRequest;
import com.delivery.dto.MessageResponse;
import com.delivery.dto.SignUpRequest;
import com.delivery.entity.Role;
import com.delivery.exception.LoginIsBusyException;
import com.delivery.security.UserDetailsImpl;
import com.delivery.security.jwt.JwtUtils;
import com.delivery.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private UserService userService;

	@Autowired
	JwtUtils jwtUtils;

	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtUtils.generateJwtToken(authentication);
		
		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
		List<String> roles = userDetails.getAuthorities().stream()
				.map(item -> item.getAuthority())
				.collect(Collectors.toList());

		return ResponseEntity.ok(new JwtResponse(jwt, 
												 userDetails.getId(), 
												 userDetails.getUsername(),
												 roles));
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
