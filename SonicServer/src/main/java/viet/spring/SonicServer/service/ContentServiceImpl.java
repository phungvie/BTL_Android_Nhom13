package viet.spring.SonicServer.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

import reactor.core.publisher.Mono;
import viet.spring.SonicServer.payload.accessToken;

import java.util.logging.Logger;

@Service
public class ContentServiceImpl implements ContentService {
    
    private static final Logger LOGGER = Logger.getLogger(ContentServiceImpl.class.getName());
    
    private final WebClient webClient;
    @Value("${sonic.github.client-id}")
    private String client_id ;
    @Value("${sonic.github.client-secret}")
    private  String client_secret ;
    @Value("${sonic.github.redirect-uri}")
    private  String redirect_uri;
    
    public ContentServiceImpl() {
        this.webClient = WebClient.builder().baseUrl("https://github.com")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .filter(logRequest())
                .build();
    }

    @Override
    public Mono<accessToken> getToken(String code) {
        String uri = UriComponentsBuilder.fromPath("/login/oauth/access_token")
                .queryParam("client_id", client_id)
                .queryParam("client_secret", client_secret)
                .queryParam("code", code)
                .build().toUriString();
        System.out.println(uri);
        return webClient.post().uri(uri).retrieve().bodyToMono(accessToken.class);
    }
    
    private ExchangeFilterFunction logRequest() {
        return (clientRequest, next) -> {
            LOGGER.info("Request: {} {}" + clientRequest.method() + clientRequest.url());
            clientRequest.headers()
                    .forEach((name, values) -> values.forEach(value -> LOGGER.info("{}={}" + name + value)));
            return next.exchange(clientRequest);
        };
    }
}
