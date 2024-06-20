package kg.attractor.xfood.service;

import kg.attractor.xfood.dto.opportunity.OpportunityShowDto;
import kg.attractor.xfood.model.Opportunity;

import java.time.LocalDateTime;
import java.util.List;

import kg.attractor.xfood.dto.opportunity.OpportunityCreateDto;
import kg.attractor.xfood.dto.opportunity.OpportunityDto;
import org.springframework.security.core.Authentication;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface OpportunityService {
    Map<String, List<OpportunityDto>> getAllByExpert();

    List<OpportunityShowDto> getOppotunitiesByDate(LocalDate date);

    Long save(Opportunity opportunity);

    List<OpportunityDto> getAllByExpertAndDate(String expertEmail, LocalDate date);

    void changeExpertOpportunities(List<OpportunityCreateDto> dtos, Authentication auth);
}
