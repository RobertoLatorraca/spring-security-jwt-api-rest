package ar.latorraca.security.api.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import ar.latorraca.security.api.dao.Authority;
import ar.latorraca.security.api.dto.AuthRequest;
import ar.latorraca.security.api.jwt.JwtUtils;
import ar.latorraca.security.api.services.UserService;

@Controller
@RequestMapping(AuthController.AUTH)
public class AuthController {

	protected static final String AUTH = "/auth";
	private static final String LOGIN = "/login";
	private static final String REGISTER = "/register";
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@PostMapping(LOGIN)
	public ResponseEntity<?> login(@RequestBody AuthRequest request) {
		try {
			Authentication authenticate = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
			User user = (User) authenticate.getPrincipal();
			return ResponseEntity.ok()
					.header(HttpHeaders.AUTHORIZATION, JwtUtils.generateAccessToken(user))
					.build();
		} catch (Exception e) {
			System.out.println();
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
	}
	
	@PostMapping(REGISTER)
	public ResponseEntity<?> register(@RequestBody AuthRequest request) {
		if (userService.findByUsername(request.getUsername()) != null)
				throw new RuntimeException(); // TODO change exception type
		UserDetails user = User.withUsername(request.getUsername())
				.password(passwordEncoder.encode(request.getPassword()))
				.authorities(Authority.ROLE_USER.toString())
				.build();
		return ResponseEntity.ok()
				.header(HttpHeaders.AUTHORIZATION, JwtUtils.generateAccessToken(userService.save(user)))
				.build();
	}
	
}
