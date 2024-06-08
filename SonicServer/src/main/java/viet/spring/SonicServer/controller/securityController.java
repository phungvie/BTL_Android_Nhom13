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
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.collect.Lists;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.val;
import viet.spring.SonicServer.DTO.UserDTO;
import viet.spring.SonicServer.DTO.VietMessage;
import viet.spring.SonicServer.config.UserImplUserDetails;
import viet.spring.SonicServer.entity.Role;
import viet.spring.SonicServer.entity.User;
import viet.spring.SonicServer.DTO.LoginRequest;
import viet.spring.SonicServer.DTO.accessToken;
import viet.spring.SonicServer.DTO.userGit;
import viet.spring.SonicServer.repository.RoleRepository;
import viet.spring.SonicServer.repository.UserRepository;
import viet.spring.SonicServer.jwt.JwtTokenProvider;
import viet.spring.SonicServer.service.UserService;
import viet.spring.SonicServer.service.gitHubAPIService;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@CrossOrigin(origins = "http://localhost:3000", maxAge = 3600,allowCredentials = "true")

@RestController
@RequestMapping("/security")
@AllArgsConstructor
public class securityController {
	AuthenticationManager authenticationManager;

	private JwtTokenProvider tokenProvider;

	private RoleRepository roleR;

	private BCryptPasswordEncoder encoder;

	private UserService userService;

	private UserRepository userR;

	private gitHubAPIService gitHubAPIService;

	@PostMapping("/login")
	public accessToken authenticateUser(@RequestBody LoginRequest loginRequest, HttpServletResponse response) {

		// Xác thực từ username và password.
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername().trim(), loginRequest.getPassword()));
		// Nếu không xảy ra exception tức là thông tin hợp lệ

		// Set thông tin authentication vào Security Context
		SecurityContextHolder.getContext().setAuthentication(authentication);

		// Trả về jwt cho người dùng.
		UserImplUserDetails viet = new UserImplUserDetails(authentication.getName(), loginRequest.getPassword(),
				(Collection<GrantedAuthority>) authentication.getAuthorities());
		String token = tokenProvider.generateToken(viet);

		Cookie cookie = new Cookie("Authorization", "Bearer_"+token );
		  // Set các thuộc tính cho cookie
        cookie.setMaxAge(3600); // Thời gian sống của cookie (đơn vị: giây)
        cookie.setPath("/"); // Đường dẫn của cookie
        cookie.setDomain("localhost"); // Domain của cookie (localhost)
        cookie.setSecure(false); // Đánh dấu cookie chỉ được truy cập qua giao thức HTTPS hay không
        cookie.setHttpOnly(true); // Chỉ cho phép truy cập qua HTTP, không cho phép qua JavaScript
        // Gắn cookie vào response
		response.addCookie(cookie);

		return new accessToken(token, "Bearer ", authentication.getAuthorities().toString());

	}

	@PostMapping("/login_github")
	public ResponseEntity<?> getTokenC(@RequestParam("code") String code, HttpServletResponse response) {
		accessToken accessToken = gitHubAPIService.getTokenGitHub(code).block();
		if (accessToken != null) {
			userGit userGit = gitHubAPIService.getUserGitHub(accessToken).block();
			if (userService.findByUsername(userGit.getBio()).isEmpty()) {
				UserDTO viet = UserDTO.builder().name(userGit.getName()).phoneNumber(userGit.getBio())
						.img(userGit.getAvatarUrl()).build();
				this.AddUser(viet, encoder.encode("123"));
				return ResponseEntity.ok().body("đăng kí thành công");
			} else {
				accessToken tokenlogin = this.authenticateUser(new LoginRequest(userGit.getBio(), "123"), response);
				return ResponseEntity.ok().body(tokenlogin);
			}
		} else {
			return ResponseEntity.badRequest().body(new VietMessage(400, "code github sai"));
		}

	}

	@GetMapping("/user")
	public ResponseEntity<?> user(@AuthenticationPrincipal OAuth2User principal) {
		return ResponseEntity.ok().body(Collections.singletonMap("name", principal.getAttribute("name")));
	}

	@GetMapping("/getUser")
	public UserDTO getUser(Principal viet) {
		Optional<User> vietdz = userService.findByUsername(viet.getName());
		UserDTO vietDTO = new UserDTO(vietdz.get());
		return vietDTO;
	}

	@PostMapping("/signup/user")
	public ResponseEntity<String> AddUser(@RequestBody UserDTO viet, String password) {
		if (viet == null) {
			return ResponseEntity.badRequest().body("Đối tượng UserDTO là rỗng");
		} else {
			Role role1 = roleR.findById(1).orElse(null);
//	    		Role role2 = roleR.findById(2).orElse(null);
			Role role3 = roleR.findById(3).orElse(null);

			User sonic = new User(viet);
			sonic.setPassword(password);
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
