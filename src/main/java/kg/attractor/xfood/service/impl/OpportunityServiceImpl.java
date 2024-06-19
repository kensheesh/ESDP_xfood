package kg.attractor.xfood.service.impl;

import kg.attractor.xfood.dto.opportunity.DailyOpportunityShowDto;
import kg.attractor.xfood.dto.opportunity.OpportunityShowDto;
import kg.attractor.xfood.model.Opportunity;
import kg.attractor.xfood.model.User;
import kg.attractor.xfood.repository.OpportunityRepository;
import jakarta.transaction.Transactional;
import kg.attractor.xfood.AuthParams;
import kg.attractor.xfood.dto.opportunity.OpportunityCreateDto;
import kg.attractor.xfood.dto.opportunity.OpportunityDto;
import kg.attractor.xfood.exception.ShiftIntersectionException;
import kg.attractor.xfood.service.OpportunityService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import java.time.LocalDate;
import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class OpportunityServiceImpl implements OpportunityService {
    private final OpportunityRepository opportunityRepository;
    private final DtoBuilder dtoBuilder;
    private final ModelBuilder modelBuilder;

    private final UserServiceImpl userService;

    @Override
    public List<OpportunityShowDto> getOppotunitiesByDate(LocalDateTime date) {
        List<Opportunity> opportunities = opportunityRepository.findByDateOrderByUser_SurnameAsc(date.toLocalDate());

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

    @Override
    public Long save(Opportunity opportunity) {
        Long id =  opportunityRepository.save(opportunity).getId();
        log.info("opportunity saved {}", opportunity);
        return id;
    }

    private OpportunityShowDto createDailyOpportunityDto(User user, LocalDateTime date) {
        OpportunityShowDto dto = new OpportunityShowDto();
        List<DailyOpportunityShowDto> shifts = new ArrayList<>();

        List<Opportunity> expertsOpportunities = opportunityRepository.findByUser_IdAndDateOrderByStartTimeAsc(user.getId(), date.toLocalDate());

        dto.setUser(dtoBuilder.buildExpertShowDto(user));
        expertsOpportunities.forEach(e -> {
            DailyOpportunityShowDto shift = new DailyOpportunityShowDto();
//            shift.setDate(e.getDate());
            shift.setStartTime(e.getStartTime());
            shift.setEndTime(e.getEndTime());
            shifts.add(shift);
        });
        dto.setShifts(shifts);
        return dto;
    }

    @Override
    public Map<String, List<OpportunityDto>> getAllByExpert() {
        String expertEmail = AuthParams.getPrincipal().getUsername();

        int dayOfWeek = LocalDateTime.now().getDayOfWeek().getValue();
        LocalDateTime monday;
        LocalDateTime sunday = LocalDate.now().plusDays(7 - dayOfWeek).atTime(23, 59);

        if (dayOfWeek == 1) {
            monday = LocalDate.now().atStartOfDay();
        } else {
            monday = LocalDate.now().minusDays(dayOfWeek - 1).atStartOfDay();
        }

        List<Opportunity> models = opportunityRepository.findAllByUserEmailAndDateBetween(expertEmail, monday, sunday);

        Map<String, List<OpportunityDto>> result = new TreeMap<>();

        for (LocalDate date = monday.toLocalDate();
             date.isBefore(sunday.toLocalDate()) || date.isEqual(sunday.toLocalDate());
             date = date.plusDays(1)) {
            result.put(date.toString(), new ArrayList<>());
        }

        models.forEach(opportunity ->
            result.get(opportunity.getDate().toString()).add(dtoBuilder.buildOpportunityDto(opportunity))
        );

        return result;
        // TODO возможно не увидит графики понедельника 00:00 и воскресенья 23:59
    }

    @Override
    public List<OpportunityDto> getAllByExpertAndDate(String expertEmail, LocalDate date) {
        return opportunityRepository.findAllByUserEmailAndDateBetween(
                expertEmail, date.atStartOfDay(), date.atTime(23, 59)
                )
                .stream()
                .map(dtoBuilder::buildOpportunityDto)
                .toList();
    }

    @Override
    @Transactional
    public void changeExpertOpportunities (List<OpportunityCreateDto> dtos, Authentication auth) {

        User expert = userService.getByEmail(auth.getName());

        List<Opportunity> opportunities = dtos.stream()
                .map(dto -> modelBuilder.buildNewOpportunity(dto, expert))
                .sorted(Comparator.comparing(Opportunity::getStartTime))
                .toList();

        for (int i = 0; i < opportunities.size(); i++) {
            if (opportunities.get(i).getStartTime().isAfter(opportunities.get(i).getEndTime())) {
                throw new IllegalArgumentException("Некорректное время");
            }

            if (i > 0) {
                if (opportunities.get(i-1).getEndTime().isAfter(opportunities.get(i).getStartTime())) {
                    throw new ShiftIntersectionException("Смены не могут пересекаться");
                }
            }

            Opportunity opportunity = opportunities.get(i);
            opportunityRepository.save(opportunity);
        }
    }
}
