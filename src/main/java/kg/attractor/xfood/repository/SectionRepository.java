package kg.attractor.xfood.repository;

import kg.attractor.xfood.model.Section;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SectionRepository extends JpaRepository<Section, Long> {
    Section findByName(String name);
}
