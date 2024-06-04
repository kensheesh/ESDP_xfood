package kg.attractor.xfood.repository;

import kg.attractor.xfood.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	
	Optional<User> getByEmail(String username);
	
	Boolean existsByEmail(String email);
}
