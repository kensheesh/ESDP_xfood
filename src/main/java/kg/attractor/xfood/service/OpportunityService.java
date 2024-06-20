package kg.attractor.xfood.service;

import kg.attractor.xfood.dto.opportunity.OpportunityCreateWrapper;
import kg.attractor.xfood.dto.opportunity.OpportunityShowDto;
import kg.attractor.xfood.model.Opportunity;

import java.time.LocalDateTime;
import java.util.List;

import kg.attractor.xfood.dto.opportunity.OpportunityDto;
import org.springframework.security.core.Authentication;

import java.time.LocalDate;
import java.util.Map;

public interface OpportunityService {
    Map<String, List<OpportunityDto>> getAllByExpert();

    List<OpportunityShowDto> getOppotunitiesByDate(LocalDateTime date);

    Long save(Opportunity opportunity);

    List<OpportunityDto> getAllByExpertAndDate(String expertEmail, LocalDate date);

    void changeExpertOpportunities(OpportunityCreateWrapper wrapper, Authentication auth);
}
