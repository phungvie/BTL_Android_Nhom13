package viet.spring.SonicServer.controller;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import viet.spring.SonicServer.jwt.JwtTokenProvider;
import viet.spring.SonicServer.repository.RoleRepository;
import viet.spring.SonicServer.repository.UserRepository;
import viet.spring.SonicServer.service.UserService;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
@CrossOrigin(origins = "http://localhost:3000", maxAge = 3600)
@RestController
@AllArgsConstructor
public class testController {
	@GetMapping("/")
	public String viet(String code) {
		return code;
	}

}
