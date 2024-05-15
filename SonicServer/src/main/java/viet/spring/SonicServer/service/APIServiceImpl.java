package viet.spring.SonicServer.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

import reactor.core.publisher.Mono;
import viet.spring.SonicServer.DTO.accessToken;
import viet.spring.SonicServer.DTO.userGit;

import java.util.logging.Logger;

@Service
public class APIServiceImpl implements gitHubAPIService {

	private static final Logger LOGGER = Logger.getLogger(APIServiceImpl.class.getName());

	private WebClient webClient;
	private WebClient webClient2;
	@Value("${sonic.github.client-id}")
	private String client_id;
	@Value("${sonic.github.client-secret}")
	private String client_secret;
	@Value("${sonic.github.redirect-uri}")
	private String redirect_uri;

	public APIServiceImpl() {
		this.webClient = WebClient.builder().baseUrl("https://github.com")
				.defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
				.defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE).filter(logRequest()).build();
	}

	private ExchangeFilterFunction logRequest() {
		return (clientRequest, next) -> {
			LOGGER.info("Request: {} {}" + clientRequest.method() + clientRequest.url());
			clientRequest.headers()
					.forEach((name, values) -> values.forEach(value -> LOGGER.info("{}={}" + name + value)));
			return next.exchange(clientRequest);
		};
	}

	@Override
	public Mono<accessToken> getTokenGitHub(String code) {
		String uri = UriComponentsBuilder.fromPath("/login/oauth/access_token").queryParam("client_id", client_id)
				.queryParam("client_secret", client_secret).queryParam("code", code).build().toUriString();

		return webClient.post().uri(uri).retrieve().bodyToMono(accessToken.class);
	}

	@Override
	public Mono<userGit> getUserGitHub(accessToken accessToken) {
		if(this.webClient2!=null) {
			this.webClient2.delete();
		}
		this.webClient2 = WebClient.builder().baseUrl("https://api.github.com")
				.defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
				.defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
				.defaultHeader("Authorization", accessToken.getToken_type() +" "+ accessToken.getAccess_token())
				.filter(logRequest()).build();
		return webClient2.get().uri("/user").retrieve().bodyToMono(userGit.class);

	}

}
