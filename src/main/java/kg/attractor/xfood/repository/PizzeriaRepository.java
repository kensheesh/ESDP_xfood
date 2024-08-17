package kg.attractor.xfood.repository;

import kg.attractor.xfood.dto.pizzeria.PizzeriaShowDto;
import kg.attractor.xfood.model.Pizzeria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PizzeriaRepository extends JpaRepository<Pizzeria, Long> {
	List<Pizzeria> findByLocation_Id(Long id);
	
	Pizzeria findByUuidEqualsIgnoreCase(String uuid);

    List<PizzeriaShowDto> findByNameContainingIgnoreCase(String query);
	
	Page<Pizzeria> findByLocation_Id(Pageable pageable, Long locationId);
	
	Page<Pizzeria> findBy(Pageable pageable);
	
}
