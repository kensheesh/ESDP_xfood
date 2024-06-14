package kg.attractor.xfood.controller.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/criteria")
@PreAuthorize("hasAnyRole('SUPERVISOR','ADMIN')")
public class CriterionController {
    
    @GetMapping("/checkType/{id}")
    @PreAuthorize("hasAnyAuthority('admin:read','supervisor:read')")
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
}
