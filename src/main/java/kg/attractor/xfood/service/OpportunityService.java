package kg.attractor.xfood.service;

import kg.attractor.xfood.dto.opportunity.OpportunityCreateDto;
import kg.attractor.xfood.dto.opportunity.OpportunityShowDto;
import kg.attractor.xfood.model.Opportunity;

import java.util.List;

import kg.attractor.xfood.dto.opportunity.OpportunityDto;

import java.time.LocalDate;
import java.util.Map;

public interface OpportunityService {
    Map<String, OpportunityDto> getAllByExpert();

    List<OpportunityShowDto> getOppotunitiesByDate(LocalDate date);

    Long save(Opportunity opportunity);

    OpportunityDto getByExpertAndDate(LocalDate date);

    void changeExpertOpportunities(OpportunityCreateDto dto);
}
