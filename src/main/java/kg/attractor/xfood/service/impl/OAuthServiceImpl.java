//package kg.attractor.xfood.service.impl;
//
//import com.fasterxml.jackson.databind.JsonNode;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import io.github.cdimascio.dotenv.Dotenv;
//import kg.attractor.xfood.service.OAuthService;
//import lombok.RequiredArgsConstructor;
//import okhttp3.*;
//import org.springframework.stereotype.Service;
//
//import java.io.IOException;
//import java.nio.charset.StandardCharsets;
//import java.security.MessageDigest;
//import java.security.NoSuchAlgorithmException;
//import java.util.Base64;
//import java.util.Objects;
//
//@Service
//@RequiredArgsConstructor
//public class OAuthServiceImpl implements OAuthService {
//
//	private static final Dotenv dotenv = Dotenv.load();
//	private static final String CLIENT_ID = dotenv.get("CLIENT_ID");
//	private static final String SECRET = dotenv.get("SECRET");
//	private static final String AUTH_SCOPE = dotenv.get("AUTH_SCOPE");
//	private static final String CODE_VERIFIER = dotenv.get("CODE_VERIFIER");
//	private static final String GRANT_TYPE = dotenv.get("GRANT_TYPE");
//	private static final String RESPONSE_TYPE = dotenv.get("RESPONSE_TYPE");
//	private static final String REDIRECT_URI = dotenv.get("REDIRECT_URI");
//	private static final String AUTH_URL = dotenv.get("AUTH_URL_RU_BY");
//
//	private final OkHttpClient client = new OkHttpClient();
//
//	private String getAuthorizationUrl() {
//		return String.format(
//				"%s?" +
//						"response_type=%s" +
//						"&client_id=%s" +
//						"&redirect_uri=%s" +
//						"&scope=%s" +
//						"&code_challenge=%s" +
//						"&code_challenge_method=S256",
//				AUTH_URL, RESPONSE_TYPE, CLIENT_ID,
//				REDIRECT_URI, AUTH_SCOPE, CODE_VERIFIER
//		);
//	}
//
//	public String getAccessToken(String authCode) throws IOException {
//		RequestBody formBody = new FormBody.Builder()
//				.add("grant_type", GRANT_TYPE)
//				.add("code", authCode)
//				.add("redirect_uri", REDIRECT_URI)
//				.add("client_id", CLIENT_ID)
//				.add("client_secret", SECRET)
//				.add("code_verifier", CODE_VERIFIER)
//				.build();
//
//		Request request = new Request.Builder()
//				.url("https://auth.dodois.io/connect/token")
//				.post(formBody)
//				.addHeader("Content-Type", "application/x-www-form-urlencoded")
//				.build();
//
//		try (Response response = client.newCall(request).execute()) {
//			if (!response.isSuccessful()) {
//				throw new IOException("Unexpected code " + response);
//			}
//			return response.body().string();
//		}
//	}
//}