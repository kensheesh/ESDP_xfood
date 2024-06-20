package kg.attractor.xfood.service.impl;

import kg.attractor.xfood.dto.opportunity.OpportunityCreateDto;
import kg.attractor.xfood.model.Opportunity;
import kg.attractor.xfood.model.User;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;

@Component
public class ModelBuilder {

    protected Opportunity buildNewOpportunity(OpportunityCreateDto dto, LocalDate date, User user) {
        return Opportunity.builder()
                .id(dto.getId())
                .user(user)
                .date(date)
                .startTime(LocalTime.of(dto.getStartTimeHour(), dto.getStartTimeMinute()))
                .endTime(LocalTime.of(dto.getEndTimeHour(), dto.getEndTimeMinute()))
                .build();
    }
}
