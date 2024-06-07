package kg.attractor.xfood.repository;

import kg.attractor.xfood.model.CheckType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CheckTypeRepository extends JpaRepository<CheckType, Long> {
}
