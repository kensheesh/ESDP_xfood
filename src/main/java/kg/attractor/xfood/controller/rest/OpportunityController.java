package kg.attractor.xfood.controller.rest;

import kg.attractor.xfood.dto.opportunity.OpportunityShowDto;
import kg.attractor.xfood.AuthParams;
import kg.attractor.xfood.dto.opportunity.OpportunityDto;
import kg.attractor.xfood.service.OpportunityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController("opportunityControllerRest")
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('SUPERVISOR','ADMIN', 'EXPERT')")
@RequestMapping("/api")
public class OpportunityController {

    private final OpportunityService opportunityService;

    @GetMapping("/opportunities/bydate/{date}")
    @PreAuthorize("hasAnyAuthority('admin:read','supervisor:read')")
    public ResponseEntity<List<OpportunityShowDto>> getOpportunitiesByDate (
            @PathVariable(name = "date") LocalDate date
    ) {
        List<OpportunityShowDto> dtos = opportunityService.getOppotunitiesByDate(date);
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/opportunities")
    public ResponseEntity<OpportunityDto> getAllByExpertAndDate (@RequestParam (name = "d") LocalDate date) {
        OpportunityDto opportunity = opportunityService.getByExpertAndDate(date);
        return ResponseEntity.ok(opportunity);
    }
}
