package viet.spring.SonicServer.service;

import reactor.core.publisher.Mono;
import viet.spring.SonicServer.payload.accessToken;

public interface ContentService {
    public Mono<accessToken> getToken(String code);
}
