package kg.attractor.xfood.repository;

import kg.attractor.xfood.model.Opportunity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface OpportunityRepository extends JpaRepository<Opportunity, Long> {
    List<Opportunity> findAllByUserEmailAndDateBetween(String userEmail, LocalDate after, LocalDate before);

    Optional<Opportunity> findByUserEmailAndDate(String userEmail, LocalDate date);

    @Query("select o from Opportunity o where o.date = ?1")
    List<Opportunity> findByDate(LocalDateTime date);

//    @Query("SELECT o FROM Opportunity o WHERE o.user.id = :id AND FUNCTION('DATE', o.date) = :date ORDER BY o.startTime")
//    List<Opportunity> findByUser_IdAndDateOrderByStartTimeAsc(@Param("id") Long id, @Param("date") LocalDate date);

    @Query("SELECT o FROM Opportunity o WHERE FUNCTION('DATE', o.date) = :date ORDER BY o.user.surname ASC")
    List<Opportunity> findByDateOrderByUser_SurnameAsc(@Param("date") LocalDate date);

    @Modifying
    void deleteAllByUserEmailAndDate(String userEmail, LocalDate date);

    @Modifying
    void deleteAllByIdNotIn(List<Long> ids);

    @Modifying
    void deleteByIdIn(List<Long> ids);

    @Query("SELECT o.id from Opportunity o WHERE o.date = ?2 and o.user.email = ?1")
    List<Long> findAllIdsByUserEmailAndDate(String userEmail, LocalDate date);
}
