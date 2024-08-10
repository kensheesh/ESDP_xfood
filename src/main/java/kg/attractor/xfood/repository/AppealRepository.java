package kg.attractor.xfood.repository;

import kg.attractor.xfood.model.Appeal;
import kg.attractor.xfood.model.CheckListsCriteria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AppealRepository extends JpaRepository<Appeal, Long>, JpaSpecificationExecutor<Appeal> {

    Optional<Appeal> findAppealById(long id);

    Page<Appeal> findAllByIsAccepted(Boolean isAccepted, Pageable pageable);

    Integer countAllByIsAcceptedNull();

    List<Appeal> findByCheckListsCriteria(CheckListsCriteria checkListsCriteria);

    @Query("select a from Appeal a " +
            "where a.checkListsCriteria.criteria.id = :criteriaId " +
            "and a.checkListsCriteria.checklist.id = :checklistId " +
            "and a.isAccepted = true")
    List<Appeal> findAcceptedAppeals(Long checklistId, Long criteriaId);
}