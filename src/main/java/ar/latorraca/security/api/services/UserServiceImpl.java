package ar.latorraca.security.api.services;

import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import ar.latorraca.security.api.dao.Authority;
import ar.latorraca.security.api.dao.AuthorityEntity;
import ar.latorraca.security.api.dao.UserEntity;
import ar.latorraca.security.api.dao.UserRepository;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails save(UserDetails userDetails) {
		UserEntity userEntity = new UserEntity();
		userEntity.setUsername(userDetails.getUsername());
		userEntity.setPassword(userDetails.getPassword());
		userEntity.setAuthorities(
				userDetails.getAuthorities().stream().map(
						a -> {
							AuthorityEntity authorityEntity = new AuthorityEntity();
							authorityEntity.setUser(userEntity);
							authorityEntity.setAuthority(Authority.valueOf(a.getAuthority()));
							return authorityEntity;
						})
						.collect(Collectors.toSet())
				);
		return userRepository.save(userEntity);
	}

	@Override
	public UserDetails findByUsername(String username) {
		Optional<UserEntity> userOptional = userRepository.findByUsername(username);
		if (userOptional.isPresent()) {
			return userOptional.get();
		} else {
			return null;
		}
	}

}
