package kg.attractor.xfood.repository;

import kg.attractor.xfood.model.Manager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ManagerRepository extends JpaRepository<Manager, Long> {
	Manager findByUuid(String staffId);

	Optional<Manager> getByPhoneNumber(String staffId);

	@Query("select m from Manager m order by m.surname")
	List<Manager> findByOrderBySurnameAsc();

	@Override
	Optional<Manager> findById(Long id);
}
