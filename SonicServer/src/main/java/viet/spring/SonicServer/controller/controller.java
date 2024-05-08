package viet.spring.SonicServer.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.beans.factory.annotation.Value;
@Controller
public class controller {
    @Value("${spring.security.oauth2.client.registration.github.client-id}")
    private String clientId;
	@GetMapping("/viet/oauth2/authorization/github")
    public String oauth2Github() {
        return "redirect:https://github.com/login/oauth/authorize"+
        		"?response_type=code"+
        		"&client_id="+clientId+
        		"&scope=read:user"+
        		"&redirect_uri=http://localhost:8081"; // Chuyển hướng đến trang xác thực Github
    }	

}
