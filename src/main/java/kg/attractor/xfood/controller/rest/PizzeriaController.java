package kg.attractor.xfood.controller.rest;

import kg.attractor.xfood.dto.pizzeria.PizzeriaDto;
import kg.attractor.xfood.service.PizzeriaService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/pizzerias")
@RequiredArgsConstructor
public class PizzeriaController {
    private final PizzeriaService pizzeriaService;

    @GetMapping
    public ResponseEntity<List<PizzeriaDto>> getAllPizzerias() {
        List<PizzeriaDto> pizzerias = pizzeriaService.getAllPizzerias();
        return ResponseEntity.ok(pizzerias);
    }

    // ROLE: SUPERVISOR
    @GetMapping("/location/{id}")
    public ResponseEntity<?> getPizzeriasByLocation(
            @PathVariable(name = "id") Long locationId
    ) {

        /*
            TODO:
                Получение списка пиццерий по локации
                (для назначения проверки)
                ------
                В PizzeriaDto также должны передаваться типы проверок
                (КЛН, одна камера и т.д.)
        */

        return null;
    }
}
