package kg.attractor.xfood.repository;

import org.springframework.boot.autoconfigure.cache.CacheType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CriteriaTypeRepository extends JpaRepository<CacheType, Integer> {
}
