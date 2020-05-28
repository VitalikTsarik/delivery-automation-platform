package com.delivery.services;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.delivery.entity.User;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class UserDetailsImpl implements UserDetails {
	private static final long serialVersionUID = 1L;

	private Long id;

	private String username;

	@JsonIgnore
	private String password;

	private GrantedAuthority authority;

	public UserDetailsImpl(Long id, String username, String password, GrantedAuthority authority) {
		this.id = id;
		this.username = username;
		this.password = password;
		this.authority = authority;
	}

	public static UserDetailsImpl build(User user) {
		GrantedAuthority authority = new SimpleGrantedAuthority(user.getRole().name());

		return new UserDetailsImpl(
				user.getId(), 
				user.getLogin(),
				user.getPassword(), 
				authority
		);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public String getUsername() {
		return username;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return Collections.singleton(authority);
	}

	@Override
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public GrantedAuthority getAuthority() {
		return authority;
	}

	public void setAuthority(GrantedAuthority authority) {
		this.authority = authority;
	}
}
