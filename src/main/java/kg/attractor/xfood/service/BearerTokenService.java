package kg.attractor.xfood.service;

public interface BearerTokenService {
	void setBearerForSupervisors(String bearerToken, Long expirySeconds);
	
	void setBearer(String username, String bearer, Long lifeTime);
}
