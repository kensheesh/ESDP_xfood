package kg.attractor.xfood.controller.rest;

import kg.attractor.xfood.dto.opportunity.OpportunityShowDto;
import kg.attractor.xfood.dto.pizzeria.PizzeriaShowDto;
import kg.attractor.xfood.service.impl.OpportunityServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController("opportunityControllerRest")
@RequiredArgsConstructor
@RequestMapping("/api/opportunities")
public class OpportunityController {
    private final OpportunityServiceImpl opportunityService;

    @GetMapping("/bydate/{date}")
    public ResponseEntity<List<OpportunityShowDto>> getPizzeriasByLocation (
            @PathVariable(name = "date") LocalDateTime date
    ) {
        List<OpportunityShowDto> dtos = opportunityService.getOppotunitiesByDate(date);

        return ResponseEntity.ok(dtos);
    }
}
