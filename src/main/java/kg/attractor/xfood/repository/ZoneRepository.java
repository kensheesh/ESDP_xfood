package kg.attractor.xfood.repository;

import kg.attractor.xfood.model.Zone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ZoneRepository extends JpaRepository<Zone, Long> {
    Zone findByName(String name);
}
