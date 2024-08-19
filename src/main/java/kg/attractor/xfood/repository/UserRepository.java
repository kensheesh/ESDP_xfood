package kg.attractor.xfood.repository;

import io.lettuce.core.dynamic.annotation.Param;
import jakarta.transaction.Transactional;
import kg.attractor.xfood.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {

    Optional<User> getByEmail(String email);
    
    Optional<User> findByEmail(String email);

    @Query(
            """
                    SELECT u from User u
                    where CAST(u.role as text) = ?1
                    and u.enabled=true
                    """
    )
    List<User> findByRole(String role);
    
    @Transactional
    @Modifying
    @Query(
            value = """
                    INSERT INTO users (name, surname, email, password, tg_link, role)
                    VALUES (:name, :surname, :email, :password, :tgLink, CAST(:role as public.role))
                    """, nativeQuery = true)
    void saveUser(@Param("name") String name,
                  @Param("surname") String surname,
                  @Param("email") String email,
                  @Param("password") String password,
                  @Param("tgLink") String tgLink,
                  @Param("role") String role);
}
