package kg.attractor.xfood.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import kg.attractor.xfood.model.BearerToken;
import kg.attractor.xfood.model.User;
import kg.attractor.xfood.repository.BearerTokenRepository;
import kg.attractor.xfood.service.BearerTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BearerTokenServiceImpl implements BearerTokenService {
	
	private final BearerTokenRepository bearerTokenRepository;
	private final UserServiceImpl userService;
	private final ObjectMapper objectMapper = new ObjectMapper();
	
	public void saveBearerToken(String json, String username) {
		BearerToken bearerToken = objectMapper.convertValue(json, BearerToken.class);
		bearerToken.setUser(userService.getByEmail(username));
		bearerTokenRepository.saveAndFlush(bearerToken);
	}
	
	@Override
	public void setBearerForSupervisors(String bearerToken, Long expirySeconds) {
		List<User> supervisors = userService.findSupervisors();
		supervisors.forEach(e -> bearerTokenRepository.saveAndFlush(
				BearerToken
						.builder()
						.token(bearerToken)
						.user(e)
						.expirySeconds(expirySeconds)
						.build()
		));
	}
	
	@Override
	public void setBearer(String username, String bearer, Long lifeTime) {
		bearerTokenRepository
				.saveAndFlush(
						BearerToken.builder()
								.token(bearer)
								.user(userService.getByEmail(username))
								.expirySeconds(lifeTime)
								.build()
				);
	}
}
