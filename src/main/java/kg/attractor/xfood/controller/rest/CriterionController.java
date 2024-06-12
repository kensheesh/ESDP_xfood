package kg.attractor.xfood.controller.rest;

import jakarta.validation.Valid;
import kg.attractor.xfood.dto.criteria.CriteriaSupervisorCreateDto;
import kg.attractor.xfood.dto.criteria.CriteriaSupervisorShowDto;
import kg.attractor.xfood.service.CriteriaService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("criterionControllerRest")
@RequiredArgsConstructor
@RequestMapping("/api/criteria")
public class CriterionController {
    private static final Logger log = LoggerFactory.getLogger(CriterionController.class);
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

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody @Valid CriteriaSupervisorCreateDto criteria, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.error(bindingResult.getFieldError().getDefaultMessage());
            return ResponseEntity.badRequest().body(criteriaService.handleValidationErrors(bindingResult));
        }
        return ResponseEntity.ok(criteriaService.create(criteria));
    }

}
