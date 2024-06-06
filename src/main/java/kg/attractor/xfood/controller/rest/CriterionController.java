package kg.attractor.xfood.controller.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/criteria")
public class CriterionController {

    /* TODO
         Получение списка критериев
         проверки после выбора типа проверки и пиццерии
         при назначении новой проверки
     */
    @GetMapping("/checkType/{id}")
    public ResponseEntity<List<?>> getAllByPizzeriaAndCheckType (
            @PathVariable (name = "id") Long checkTypeId,
            @RequestParam (name = "pizzeria")Long pizzeriaId
    ) {
        return null;
    }
}
