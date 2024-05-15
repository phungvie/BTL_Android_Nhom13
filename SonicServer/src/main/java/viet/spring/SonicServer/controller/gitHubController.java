package viet.spring.SonicServer.controller;

import java.io.ObjectInputFilter.Config;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import reactor.core.publisher.Mono;
import viet.spring.SonicServer.DTO.LoginRequest;
import viet.spring.SonicServer.DTO.UserDTO;
import viet.spring.SonicServer.DTO.accessToken;
import viet.spring.SonicServer.DTO.userGit;
import viet.spring.SonicServer.entity.User;
import viet.spring.SonicServer.service.gitHubAPIService;
import viet.spring.SonicServer.service.APIServiceImpl;
import viet.spring.SonicServer.service.UserService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@CrossOrigin(origins = "http://localhost:3000", maxAge = 3600)
@RestController
@RequestMapping("/git_hub")
@AllArgsConstructor
public class gitHubController {
	private securityController controller;
	private gitHubAPIService gitHubAPIService;
	private UserService service;
	private BCryptPasswordEncoder encoder;

	@GetMapping("/get_token")
	public ResponseEntity<?> getTokenC(@RequestParam("code") String code) {
		accessToken accessToken = gitHubAPIService.getTokenGitHub(code).block();
		userGit userGit = gitHubAPIService.getUserGitHub(accessToken).block();

		if (service.findByUsername(userGit.getBio()).isEmpty()) {
			UserDTO viet = UserDTO.builder().name(userGit.getName()).phone(userGit.getBio()).img(userGit.getAvatarUrl())
					.build();
			controller.AddUser(viet,encoder.encode("123"));
			return ResponseEntity.ok().body("đăng kí thành công");
		}else {
			accessToken tokenlogin= controller.authenticateUser(new LoginRequest(userGit.getBio(),"123"));
			return ResponseEntity.ok().body(tokenlogin);
		}
		
	}

}
