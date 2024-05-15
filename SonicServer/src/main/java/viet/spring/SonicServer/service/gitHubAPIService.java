package viet.spring.SonicServer.service;

import reactor.core.publisher.Mono;
import viet.spring.SonicServer.DTO.accessToken;
import viet.spring.SonicServer.DTO.userGit;

public interface gitHubAPIService {
    public Mono<accessToken> getTokenGitHub(String code);
    public Mono<userGit> getUserGitHub(accessToken accessToken);
}
