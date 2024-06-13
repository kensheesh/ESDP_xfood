package kg.attractor.xfood.service;

import kg.attractor.xfood.dto.opportunity.OpportunityShowDto;
import kg.attractor.xfood.model.Opportunity;

import java.time.LocalDateTime;
import java.util.List;

public interface OpportunityService {

    List<OpportunityShowDto> getOppotunitiesByDate(LocalDateTime date);

    void save(Opportunity opportunity);
}
