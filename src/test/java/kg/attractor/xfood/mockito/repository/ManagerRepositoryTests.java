package kg.attractor.xfood.mockito.repository;

import kg.attractor.xfood.model.Manager;
import kg.attractor.xfood.repository.ManagerRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
class ManagerRepositoryTests {

    @Autowired
    private ManagerRepository repository;
    @Autowired
    private ManagerRepository managerRepository;

    @Test
    void ManagerRepository_SaveAll_ReturnSavedManager() {
        Manager checkListsCriteria = Manager.builder()
                .name("leha")
                .surname("beliy")
                .phoneNumber("123")
                .uuid("123hkjhfsd129312")
                .build();

        Manager savedChecklistCriteria = repository.save(checkListsCriteria);
        Assertions.assertThat(savedChecklistCriteria).isNotNull();
        Assertions.assertThat(savedChecklistCriteria.getId()).isPositive();
    }


    @Test
    void ManagerRepository_GetAll_ReturnMoreThanOneManager() {
        Manager manager = Manager.builder()
                .name("leha")
                .surname("beliy")
                .phoneNumber("123")
                .uuid("123hkjhfsd129312")
                .build();

        Manager manager2 = Manager.builder()
                .name("alex")
                .surname("seriy")
                .phoneNumber("987987")
                .uuid("fhalshdj1394y1293ykjhfd")
                .build();

        managerRepository.save(manager);
        managerRepository.save(manager2);

        List<Manager> managerList = managerRepository.findAll();
        Assertions.assertThat(managerList).isNotNull();
        Assertions.assertThat(managerList.size()).isEqualTo(2);
    }

}

