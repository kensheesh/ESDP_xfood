package kg.attractor.xfood.controller.rest;

import kg.attractor.xfood.AuthParams;
import kg.attractor.xfood.dto.opportunity.OpportunityDto;
import kg.attractor.xfood.service.OpportunityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/")
public class OpportunityController {
    private final OpportunityService opportunityService;

    @GetMapping("/opportunities")
    public ResponseEntity<List<OpportunityDto>> getAllByExpertAndDate (@RequestParam (name = "d") LocalDate date) {
        var opportunities = opportunityService.getAllByExpertAndDate(AuthParams.getPrincipal().getUsername(), date);
        return ResponseEntity.ok(opportunities);
    }
}
