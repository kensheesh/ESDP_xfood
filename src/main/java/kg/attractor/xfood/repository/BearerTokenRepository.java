package kg.attractor.xfood.repository;

import io.lettuce.core.dynamic.annotation.Param;
import kg.attractor.xfood.model.BearerToken;
import kg.attractor.xfood.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BearerTokenRepository extends JpaRepository<BearerToken, Long> {
	
	@Query("SELECT bt FROM BearerToken bt WHERE bt.user = :user ORDER BY bt.id DESC")
	Optional<BearerToken> findLastTokenByUser(@Param("user") User user);
}
