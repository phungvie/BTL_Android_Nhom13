package viet.spring.SonicServer.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.collect.Lists;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import viet.spring.SonicServer.DTO.UserDTO;
import viet.spring.SonicServer.config.UserImplUserDetails;
import viet.spring.SonicServer.entity.Role;
import viet.spring.SonicServer.entity.User;
import viet.spring.SonicServer.payload.LoginRequest;
import viet.spring.SonicServer.payload.LoginResponse;
import viet.spring.SonicServer.repository.RoleRepository;
import viet.spring.SonicServer.repository.UserRepository;
import viet.spring.SonicServer.jwt.JwtTokenProvider;
import viet.spring.SonicServer.service.UserService;

import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/security")
@AllArgsConstructor
public class securityController {
	AuthenticationManager authenticationManager;

	private JwtTokenProvider tokenProvider;

	RoleRepository roleR;

	BCryptPasswordEncoder encoder;

	UserService userService;

	UserRepository userR;

	@PostMapping("/login")
	public LoginResponse authenticateUser(@RequestBody LoginRequest loginRequest) {

		// Xác thực từ username và password.
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername().trim(), loginRequest.getPassword()));
		// Nếu không xảy ra exception tức là thông tin hợp lệ
		
		// Set thông tin authentication vào Security Context
		SecurityContextHolder.getContext().setAuthentication(authentication);

		// Trả về jwt cho người dùng.
		UserImplUserDetails viet = new UserImplUserDetails(authentication.getName(), loginRequest.getPassword(),
				(Collection<GrantedAuthority>) authentication.getAuthorities());
		String jwt = tokenProvider.generateToken(viet);
		return new LoginResponse(jwt);

	}
	
	@GetMapping("/user")
	public ResponseEntity<?> user(@AuthenticationPrincipal OAuth2User principal) {
		return ResponseEntity.ok().body( Collections.singletonMap("name", principal.getAttribute("name")));
	}


	@GetMapping("/getUser")
	public UserDTO getUser(Principal viet) {
		Optional<User> vietdz = userService.findByUsername(viet.getName());
		UserDTO vietDTO = new UserDTO(vietdz.get());
		return vietDTO;
	}

	@PostMapping("/signup/user")
	public ResponseEntity<String> AddUser(@RequestBody UserDTO viet) {
		if (viet == null) {
			return ResponseEntity.badRequest().body("Đối tượng UserDTO là rỗng");
		} else {
			Role role1 = roleR.findById(1).orElse(null);
//	    		Role role2 = roleR.findById(2).orElse(null);
			Role role3 = roleR.findById(3).orElse(null);

			User sonic = new User(viet);
			sonic.setRoles(Lists.newArrayList(role1, role3));
			userR.save(sonic);

			return ResponseEntity.ok().body("Thêm user thành công");

		}

	}

	@PostMapping("/signup/admin")
	public ResponseEntity<String> AddAdmin(@RequestBody UserDTO viet) {
		if (viet == null) {
			return ResponseEntity.badRequest().body("Đối tượng UserDTO là rỗng");
		} else {
//		Role role1 = roleR.findById(1).orElse(null);
			Role role2 = roleR.findById(2).orElse(null);
			Role role3 = roleR.findById(3).orElse(null);

			User sonic = new User(viet);
			sonic.setRoles(Lists.newArrayList(role2, role3));
			userR.save(sonic);

			return ResponseEntity.ok().body("Thêm admin thành công");

		}
	}
	


}
