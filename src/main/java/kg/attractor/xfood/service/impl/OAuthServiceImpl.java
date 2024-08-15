package kg.attractor.xfood.service.impl;

import io.github.cdimascio.dotenv.Dotenv;
import kg.attractor.xfood.service.OAuthService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import okhttp3.*;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.time.Duration;
import java.util.Base64;

@Service
@RequiredArgsConstructor
public class OAuthServiceImpl implements OAuthService {
	
	private static final Dotenv dotenv = Dotenv.load();
	private static final String CLIENT_ID = dotenv.get("CLIENT_ID");
	private static final String SECRET = dotenv.get("SECRET");
	private static final String AUTH_SCOPE = dotenv.get("AUTH_SCOPE");
	private static final String REDIRECT_URI = dotenv.get("REDIRECT_URI");
	private static final String AUTH_URL = dotenv.get("AUTH_URL_RU_BY");
	private static final String SERVER_PORT = dotenv.get("SERVER_PORT");
	
	private final OkHttpClient client = new OkHttpClient();
	private final BearerTokenServiceImpl bearerTokenService;
//	private final String redirectTO = "https://localhost:3452/oauth/callback";
//	private final String redirectTO = "https://localhost:5001";

	private final String redirectTO = "https://localhost:" + SERVER_PORT + REDIRECT_URI;
	
	@Autowired
	private RedisTemplate<String, String> redisTemplate;
	private String codeVerifier;
	
	@Override
	public String getAuthorizationUrl() {
		
		codeVerifier = generateCodeVerifier();
		String codeChallenge = generateCodeChallenge(codeVerifier);
		redisTemplate.opsForValue().set(codeVerifier, codeChallenge, Duration.ofHours(2));
		
		
		return String.format(
				"%s/connect/authorize?" +
						"response_type=%s" +
						"&client_id=%s" +
						"&redirect_uri=%s" +
						"&scope=%s" +
						"&code_challenge=%s" +
						"&code_challenge_method=S256",
				AUTH_URL, "code", CLIENT_ID,
				redirectTO, AUTH_SCOPE, codeVerifier
		);
		
	}
	
	@Override
	@SneakyThrows
	public void getAccessToken(String authCode, String scope, String username) {
		String codeChallenge = redisTemplate.opsForValue().get(codeVerifier);
		
		RequestBody formBody = new FormBody.Builder()
				.add("client_id", CLIENT_ID)
				.add("code_verifier", codeChallenge)
				.add("scope", scope)
				.add("code", authCode)
				.add("grant_type", "authorization_code")
				.add("redirect_uri", redirectTO)
				.add("client_secret", SECRET)
				.build();
		
		Request request = new Request.Builder()
				.url(AUTH_URL + "/connect/token")
				.post(formBody)
				.addHeader("Content-Type", "application/x-www-form-urlencoded")
				.build();
		
		try (Response response = client.newCall(request).execute()) {
			if (! response.isSuccessful()) {
				throw new IOException("Unexpected code " + response);
			}
			bearerTokenService.saveBearerToken(response.body().string(), username);
		}
	}
	
	@SneakyThrows
    public String generateCodeVerifier() {
		SecureRandom secureRandom = new SecureRandom();
		byte[] codeVerifier = new byte[32];
		secureRandom.nextBytes(codeVerifier);
		return Base64.getUrlEncoder().withoutPadding().encodeToString(codeVerifier);
	}
	
	@SneakyThrows
    public String generateCodeChallenge(@NotNull String codeVerifier) {
		byte[] bytes = codeVerifier.getBytes(StandardCharsets.US_ASCII);
		MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
		messageDigest.update(bytes);
		byte[] digest = messageDigest.digest();
		return Base64.getUrlEncoder().withoutPadding().encodeToString(digest);
	}
}