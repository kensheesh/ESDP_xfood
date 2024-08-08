package kg.attractor.xfood.repository;

import kg.attractor.xfood.model.Section;
import kg.attractor.xfood.model.Setting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface SettingRepository extends JpaRepository<Setting, Long> {

    @Query("select s from Setting s where s.name = ?1")
    Optional<Setting> findByName(String name);

    @Transactional
    @Modifying
    @Query("update Setting s set s.valueInt = ?1 where s.name = ?2")
    void updateValueIntByName(Integer valueInt, String name);
}
