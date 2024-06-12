package kg.attractor.xfood.service.impl;

import kg.attractor.xfood.dto.opportunity.DailyOpportunityShowDto;
import kg.attractor.xfood.dto.opportunity.OpportunityShowDto;
import kg.attractor.xfood.model.Opportunity;
import kg.attractor.xfood.model.User;
import kg.attractor.xfood.repository.OpportunityRepository;
import kg.attractor.xfood.service.OpportunityService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class OpportunityServiceImpl implements OpportunityService {
    private final OpportunityRepository opportunityRepository;
    private final DtoBuilder dtoBuilder;

    public List<OpportunityShowDto> getOppotunitiesByDate(LocalDateTime date) {
        List<Opportunity> opportunities = opportunityRepository.findByDate(date);
        Set<User> expertsOfDay = new HashSet<>();

        opportunities.forEach(e -> {
            expertsOfDay.add(e.getUser());
        });

        List<OpportunityShowDto> dailyOpportunityDtos = new ArrayList<>();

        expertsOfDay.forEach(e -> {
            dailyOpportunityDtos.add(createDailyOpportunityDto(e, date));
        });

        return dailyOpportunityDtos;
    }

    private OpportunityShowDto createDailyOpportunityDto(User user, LocalDateTime date) {
        OpportunityShowDto dto = new OpportunityShowDto();
        List<DailyOpportunityShowDto> shifts = new ArrayList<>();

        List<Opportunity> expertsOpportunities = opportunityRepository.findByUser_IdAndDateOrderByStartTimeAsc(user.getId(), date);

        dto.setUser(dtoBuilder.buildExpertShowDto(user));
        expertsOpportunities.forEach(e -> {
            DailyOpportunityShowDto shift = new DailyOpportunityShowDto();
            shift.setDate(e.getDate());
            shift.setStartTime(e.getStartTime());
            shift.setEndTime(e.getEndTime());
            shifts.add(shift);
        });
        dto.setShifts(shifts);
        return dto;
    }
}
