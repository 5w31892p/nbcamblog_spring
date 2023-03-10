package com.sparta.nbcamblog.security;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.sparta.nbcamblog.entity.BlogUser;
import com.sparta.nbcamblog.entity.UserRoleEnum;
import com.sparta.nbcamblog.exception.CustomStatus;
import com.sparta.nbcamblog.exception.StatusEnum;
import com.sparta.nbcamblog.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserSecurityService implements UserDetailsService {
	private final UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<BlogUser> blogUser = this.userRepository.findByUsername(username);
		if (!blogUser.isPresent()) {
			throw new CustomStatus(StatusEnum.UNINFORMED_USERNAME);
		}
		BlogUser user = blogUser.get();
		List<GrantedAuthority> authorities = new ArrayList<>();
		if ("admin".equals(username)) {
			authorities.add(new SimpleGrantedAuthority(UserRoleEnum.ADMIN.getAuthority()));
		} else {
			authorities.add(new SimpleGrantedAuthority(UserRoleEnum.USER.getAuthority()));
		}
		return new User(user.getUsername(), user.getPassword(),authorities);
	}
}
