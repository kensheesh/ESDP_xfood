package kg.attractor.xfood.service;

public interface BearerTokenService {
	void setBearerForSupervisors(String bearerToken, Long expirySeconds);
}
