package kg.attractor.xfood.repository;

import jakarta.transaction.Transactional;
import kg.attractor.xfood.enums.Status;
import kg.attractor.xfood.model.CheckList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface CheckListRepository extends JpaRepository<CheckList, Long> {

    List<CheckList> findCheckListByExpertEmailAndStatus(String email, Status status);

    Optional<CheckList> findByUuidLink(String uuid);

    @Query(value = """
            SELECT c
            FROM CheckList c
            where c.uuidLink = ?1
            and CAST(c.status as text) = :#{#status.getStatus()}
            """)
    Optional<CheckList> findByIdAndStatus(String checkListId, Status status);

    @Query(value = """
            SELECT c
            FROM CheckList c
            where c.id = ?1
            and CAST(c.status as text) = :#{#status.getStatus()}
            """)
    Optional<CheckList> findByIdAndStatus(Long checkListId, Status status);

    @Query(value = """
            SELECT c
            FROM CheckList c
            WHERE CAST(c.status as text) = :#{#status.getStatus()}
            """)
    List<CheckList> findByStatus(Status status);

  /*  @Query(value = """
            SELECT c
            FROM CheckList c
                    JOIN Opportunity o ON c.opportunity.id = o.id
                     JOIN User u ON o.user.id = u.id
            WHERE CAST(c.status as text) = :#{#status.getStatus()}
            """)
    List<CheckList> findCheckListByStatus(Status status);*/

    @Modifying
    @Transactional
   @Query(value = """
            		insert into check_lists(status, work_schedule_id, expert_id, uuid_link)
            			values(CAST(CAST(:#{#status} as text) as Status),:#{#workSchedule}, :#{#expertId}, :#{#uuid_link}) ;
            """, nativeQuery = true)
    int saveCheckList(Long workSchedule, String status, Long expertId, String uuid_link);



    @Query(value = """
            SELECT c
            FROM CheckList c
            where c.uuidLink = ?1
            and CAST(c.status as text) = :#{#status.getStatus()}
            """)
    Optional<CheckList> findByUuidLinkAndStatus(String uuidLink, Status status);

    CheckList findByCheckListsCriteriaId(Long id);

    Optional<CheckList> findCheckListByWorkSchedule_IdAndExpert_Id(Long workScheduleId, Long expertId);

    boolean existsByWorkSchedule_IdAndExpert_Id(Long id, Long expertId);

    @Query("select c from CheckList  c where c.endTime between :startDate and :endDate ")
    List<CheckList> findAllByEndTimeBetween(LocalDate startDate, LocalDate endDate);
}
