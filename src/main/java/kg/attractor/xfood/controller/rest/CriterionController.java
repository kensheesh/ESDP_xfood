package kg.attractor.xfood.controller.rest;

import jakarta.validation.Valid;
import kg.attractor.xfood.dto.criteria.CriteriaSupervisorCreateDto;
import kg.attractor.xfood.dto.criteria.CriteriaSupervisorShowDto;
import kg.attractor.xfood.service.CriteriaService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/criteria")
@PreAuthorize("hasAnyRole('SUPERVISOR','ADMIN', 'EXPERT')")
public class CriterionController {
    private static final Logger log = LoggerFactory.getLogger(CriterionController.class);
    private final CriteriaService criteriaService;

    //    ROLE: SUPERVISOR
    @GetMapping("/{checkTypeId}")
    @PreAuthorize("hasAnyAuthority('admin:read','supervisor:read')")
    public ResponseEntity<List<CriteriaSupervisorShowDto>> getAllByCheckType (
            @PathVariable (name = "checkTypeId") Long checkTypeId) {
        return ResponseEntity.ok(criteriaService.getByCheckType(checkTypeId));
    }

    @GetMapping("/search")
    public ResponseEntity<List<CriteriaSupervisorShowDto>> getByDescription(@RequestParam(name = "description") String description) {
        return ResponseEntity.ok(criteriaService.getByDescription(description, true));
    }

    @GetMapping("/search/def")
    public ResponseEntity<List<CriteriaSupervisorShowDto>> getByDescriptionWithout(@RequestParam(name = "description") String description) {
        return ResponseEntity.ok(criteriaService.getByDescription(description, false));
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<CriteriaSupervisorShowDto> getById(@PathVariable (name = "id") Long id) {
        return ResponseEntity.ok(criteriaService.getById(id));
    }

    @GetMapping("/find/{checkId}/{criteriaId}")
    public ResponseEntity<CriteriaSupervisorShowDto> getById(@PathVariable (name = "checkId") Long checkId, @PathVariable(name = "criteriaId") Long criteriaId) {
        return ResponseEntity.ok(criteriaService.getByCheckAndCriteria(checkId, criteriaId));
    }

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody @Valid CriteriaSupervisorCreateDto criteria, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.error(bindingResult.getFieldError().getDefaultMessage());
            return ResponseEntity.badRequest().body(criteriaService.handleValidationErrors(bindingResult));
        }
        return ResponseEntity.ok(criteriaService.create(criteria));
    }

    @GetMapping("/critical")
    public ResponseEntity<List<CriteriaSupervisorShowDto>> getCriticalFactors() {
        var factors = criteriaService.getCritCriteria();
        return ResponseEntity.ok(factors);
    }

    @GetMapping("/wow")
    public ResponseEntity<List<CriteriaSupervisorShowDto>> getWowFactors() {
        var factors = criteriaService.getWowCriteria();
        return ResponseEntity.ok(factors);
    }
    
    

}
