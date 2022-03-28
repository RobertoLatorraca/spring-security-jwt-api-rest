package ar.latorraca.security.api.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(HelloRestController.HELLO)
public class HelloRestController {
	
	protected static final String HELLO = "/hello";
	protected static final String ADMIN = "/admin";
	protected static final String USER = "/user";
	
	@GetMapping(USER)
	public String helloUser() {
		return "Hello User";
	}
	
	@GetMapping(ADMIN)
	public String helloAdmin() {
		return "Hello Admin";
	}

}
