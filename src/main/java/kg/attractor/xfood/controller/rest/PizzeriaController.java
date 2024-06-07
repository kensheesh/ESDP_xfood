package kg.attractor.xfood.controller.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/pizzerias")
@RequiredArgsConstructor
public class PizzeriaController {

    // ROLE: SUPERVISOR
    @GetMapping("/location/{id}")
    public ResponseEntity<?> getPizzeriasByLocation (
            @PathVariable (name = "id") Long locationId
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
