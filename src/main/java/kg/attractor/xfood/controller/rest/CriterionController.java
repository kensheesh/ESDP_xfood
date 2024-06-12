package kg.attractor.xfood.controller.rest;

import kg.attractor.xfood.dto.criteria.CriteriaSupervisorShowDto;
import kg.attractor.xfood.service.CriteriaService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/criteria")
public class CriterionController {
    private final CriteriaService criteriaService;

    //    ROLE: SUPERVISOR
    @GetMapping("/checkType/{id}")
    public ResponseEntity<List<?>> getAllByPizzeriaAndCheckType (
            @PathVariable (name = "id") Long checkTypeId,
            @RequestParam (name = "pizzeria")Long pizzeriaId
    ) {

    /* TODO
         Получение списка критериев
         проверки после выбора типа проверки и пиццерии
         при назначении новой проверки
    */
        return null;
    }

    @GetMapping("/search")
    public ResponseEntity<List<CriteriaSupervisorShowDto>> getByDescription(@RequestParam(name = "description") String description) {
        return ResponseEntity.ok(criteriaService.getByDescription(description));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CriteriaSupervisorShowDto> getById(@PathVariable (name = "id") Long id) {
        return ResponseEntity.ok(criteriaService.getById(id));
    }
}
