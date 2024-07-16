package kg.attractor.xfood.repository;

import kg.attractor.xfood.model.Appeal;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppealRepository extends JpaRepository<Appeal, Long> {
    Page<Appeal> findAllByIsAccepted(Boolean isAccepted, Pageable pageable);

    Integer countAllByIsAcceptedNull ();
}
