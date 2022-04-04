package ar.latorraca.security.api.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import ar.latorraca.security.api.dao.UserEntity;
import ar.latorraca.security.api.dao.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {		
		UserEntity u = userRepository.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException("Username not found."));		
		return User.builder()
				.username(u.getUsername())
				.password(u.getPassword())
				.authorities(u.getAuthorities())
				.accountExpired(!u.isAccountNonExpired())
				.accountLocked(!u.isAccountNonLocked())
				.credentialsExpired(!u.isCredentialsNonExpired())
				.disabled(!u.isEnabled())
				.build();
	}

}
