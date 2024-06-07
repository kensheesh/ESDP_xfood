package kg.attractor.xfood.repository;

import kg.attractor.xfood.model.CheckListsCriteria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CheckListCriteriaRepository extends JpaRepository<CheckListsCriteria, Long> {

    List<CheckListsCriteria> findByChecklistId(Long id);
}
