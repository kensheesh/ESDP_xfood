package kg.attractor.xfood.controller.rest;

import kg.attractor.xfood.dto.opportunity.OpportunityShowDto;
import kg.attractor.xfood.service.impl.OpportunityServiceImpl;
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

import java.time.LocalDateTime;
import java.time.LocalDate;
import java.util.List;

@RestController("opportunityControllerRest")
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('SUPERVISOR','ADMIN')")
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

  /*  @GetMapping("/opportunities")
    public ResponseEntity<List<OpportunityDto>> getAllByExpertAndDate (@RequestParam (name = "d") LocalDate date) {
        var opportunities = opportunityService.getAllByExpertAndDate(AuthParams.getPrincipal().getUsername(), date);
        return ResponseEntity.ok(opportunities);
    }*/
    
}
