package ar.latorraca.security.api.services;

import org.springframework.security.core.userdetails.UserDetails;

public interface UserService {
	
	UserDetails findByUsername(String username);
	
	UserDetails save(UserDetails userDetails);

}
