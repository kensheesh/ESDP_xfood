package kg.attractor.xfood.repository;

import kg.attractor.xfood.model.Appeal;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AppealRepository extends JpaRepository<Appeal, Long> {

    Optional<Appeal> findAppealById(long id);

    Page<Appeal> findAllByIsAccepted(Boolean isAccepted, Pageable pageable);

    Integer countAllByIsAcceptedNull();
}