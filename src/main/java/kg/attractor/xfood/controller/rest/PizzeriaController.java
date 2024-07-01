package kg.attractor.xfood.controller.rest;

import kg.attractor.xfood.dto.pizzeria.PizzeriaDto;
import kg.attractor.xfood.dto.pizzeria.PizzeriaShowDto;
import kg.attractor.xfood.dto.pizzeria.PizzeriaWeeklyDto;
import kg.attractor.xfood.service.PizzeriaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/pizzerias")
@RequiredArgsConstructor
//@PreAuthorize("hasAnyRole('SUPERVISOR','ADMIN')")
public class PizzeriaController {
    private final PizzeriaService pizzeriaService;

    @GetMapping
    public ResponseEntity<?> getAllPizzerias() {
        List<PizzeriaDto> pizzerias = pizzeriaService.getAllPizzerias();
        return ResponseEntity.ok(pizzerias);
    }
    
    @GetMapping("/location/{id}")
    @PreAuthorize("hasAnyAuthority('admin:read','supervisor:read')")
    public ResponseEntity<List<PizzeriaWeeklyDto>> getPizzeriasByLocation(
            @PathVariable(name = "id") Long locationId) {
        List<PizzeriaWeeklyDto> dtos = pizzeriaService.getPizzeriasByLocationId(locationId);

        return ResponseEntity.ok(dtos);
    }
}
