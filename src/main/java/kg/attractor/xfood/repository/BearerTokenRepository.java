package kg.attractor.xfood.repository;

import io.lettuce.core.dynamic.annotation.Param;
import kg.attractor.xfood.model.BearerToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BearerTokenRepository extends JpaRepository<BearerToken, Long> {
	
	@Query(value = "SELECT * FROM bearer_tokens WHERE user_id = :userId ORDER BY id DESC LIMIT 1", nativeQuery = true)
	Optional<BearerToken> findLastTokenByUserId(@Param("userId") Long userId);
}
