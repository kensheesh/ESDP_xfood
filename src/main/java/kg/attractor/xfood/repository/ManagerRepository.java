package kg.attractor.xfood.repository;

import kg.attractor.xfood.model.Manager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ManagerRepository extends JpaRepository<Manager, Long> {
	Manager findByUuid(String staffId);

	Optional<Manager> getByUuid(String staffId);
}
