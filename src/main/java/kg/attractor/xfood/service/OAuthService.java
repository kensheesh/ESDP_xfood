package kg.attractor.xfood.service;

import lombok.SneakyThrows;

public interface OAuthService {
	
	String getAuthorizationUrl();
	
	@SneakyThrows
	void getAccessToken(String authCode, String scope, String username);
}
