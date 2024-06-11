package kg.attractor.xfood.controller.rest;

import kg.attractor.xfood.dto.pizzeria.PizzeriaShowDto;
import kg.attractor.xfood.service.impl.PizzeriaServiceImpl;
import lombok.RequiredArgsConstructor;
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
    private final PizzeriaServiceImpl pizzeriaService;

    // ROLE: SUPERVISOR
    @GetMapping("/location/{id}")
    public ResponseEntity<List<PizzeriaShowDto>> getPizzeriasByLocation (
            @PathVariable (name = "id") Long locationId
    ) {
        List<PizzeriaShowDto> dtos = pizzeriaService.getPizzeriaByLocationId(locationId);

        return ResponseEntity.ok(dtos);
    }
}
