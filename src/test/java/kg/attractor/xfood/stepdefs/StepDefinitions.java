package kg.attractor.xfood.stepdefs;

import io.cucumber.java.ru.Когда;
import io.cucumber.java.ru.Тогда;
import kg.attractor.xfood.dto.manager.ManagerDto;
import kg.attractor.xfood.service.ManagerService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@RequiredArgsConstructor
public class StepDefinitions {

    private ManagerDto managerDto;
    private final ManagerService managerService;

//    public StepDefinitions(ManagerDto managerDto, ManagerService managerService) {
//        this.managerDto = managerDto;
//        this.managerService = managerService;
//    }

    @Когда("я запрашиваю менеджера с id {long}")
    public void яЗапрашиваюМенеджераСId(long managerId) {
        managerDto = managerService.getManagerDtoById(managerId);

        assertEquals(managerId, managerDto.getId());
    }

    @Тогда("я получаю информацию по менеджеру")
    public void яПолучаюИнформациюПоМенеджеру() {
        assertNotNull(managerDto);
    }
}
