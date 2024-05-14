package viet.spring.SonicServer.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import reactor.core.publisher.Mono;
import viet.spring.SonicServer.payload.accessToken;
import viet.spring.SonicServer.service.ContentService;
import viet.spring.SonicServer.service.ContentServiceImpl;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@CrossOrigin(origins = "http://localhost:3000", maxAge = 3600)
@RestController
@RequestMapping("/git_hub")
@AllArgsConstructor
public class gitHubController {

    private ContentServiceImpl contentService;
    

    
    @GetMapping("/get_token")
    public Mono<accessToken> getTokenC(@RequestParam("code") String code) {
        return contentService.getToken(code);
    } 

}
