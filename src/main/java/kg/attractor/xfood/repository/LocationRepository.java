package kg.attractor.xfood.repository;

import kg.attractor.xfood.model.Location;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationRepository extends JpaRepository<Location, Long> {
}
