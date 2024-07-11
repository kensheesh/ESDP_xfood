package kg.attractor.xfood.repository;

import kg.attractor.xfood.model.File;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FileRepository extends JpaRepository<File, Long> {
	List<File> findByAppealId(Long appealId);
}
