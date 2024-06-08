package viet.spring.SonicServer.controller;

import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;


import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
//@CrossOrigin(origins = "http://localhost:3000", maxAge = 3600)
@RestController
@AllArgsConstructor
public class testController {
	@GetMapping("/")
	public String viet(String code,String token) {
		return code +"  "+token;
	}
	

}
