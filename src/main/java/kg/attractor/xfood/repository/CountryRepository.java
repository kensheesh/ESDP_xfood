package kg.attractor.xfood.repository;

import kg.attractor.xfood.model.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CountryRepository extends JpaRepository<Country, Long> {
	

	
	@Query(value = """
			SELECT * FROM countries
			where country_code IN (
			    SELECT DISTINCT country_code
			    FROM countries
			);
			""", nativeQuery = true)
	List<Country> findDistinctCountryCodes();
}
