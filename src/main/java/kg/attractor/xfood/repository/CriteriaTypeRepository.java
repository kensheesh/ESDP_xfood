package kg.attractor.xfood.repository;

import kg.attractor.xfood.model.CriteriaType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CriteriaTypeRepository extends JpaRepository<CriteriaType, Long> {
    @Query("select c from CriteriaType c where c.type.id = ?1")
    List<CriteriaType> findByType_Id(Long id);

    @Query("select c from CriteriaType c where c.type.id = ?1 and c.criteria.section.id = ?2 order by c.type.name")
    List<CriteriaType> findByType_IdAndCriteria_Section_IdOrderByType_NameAsc(Long id, Long id1);
}
