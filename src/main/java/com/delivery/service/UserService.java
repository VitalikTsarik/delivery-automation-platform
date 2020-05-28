package com.delivery.service;

import com.delivery.entity.Role;
import com.delivery.entity.User;
import com.delivery.exception.LoginIsBusyException;
import com.delivery.repository.UserRepository;
import com.delivery.security.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService implements UserDetailsService {
	@Autowired
	UserRepository userRepository;
	@Autowired
	PasswordEncoder passwordEncoder;

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
		User user = userRepository.findByLogin(login)
				.orElseThrow(() -> new UsernameNotFoundException("User Not Found with login: " + login));

		return UserDetailsImpl.build(user);
	}

	public boolean existsByLogin(String login) {
		return userRepository.existsByLogin(login);
	}

	public User signUp(
			String login,
			String password,
			String firstName,
			String secondName,
			String patronymic,
			Role role
	) throws LoginIsBusyException {
		if (existsByLogin(login)) {
			throw new LoginIsBusyException();
		}

		User user = new User();
		user.setLogin(login);
		user.setPassword(passwordEncoder.encode(password));
		user.setFirstName(firstName);
		user.setSecondName(secondName);
		user.setPatronymic(patronymic);
		user.setRole(role);

		return userRepository.save(user);
	}

}
