package kg.attractor.xfood.service.impl;

import jakarta.transaction.Transactional;
import kg.attractor.xfood.AuthParams;
import kg.attractor.xfood.dto.opportunity.DailyOpportunityShowDto;
import kg.attractor.xfood.dto.opportunity.OpportunityCreateWrapper;
import kg.attractor.xfood.dto.opportunity.OpportunityDto;
import kg.attractor.xfood.dto.opportunity.OpportunityShowDto;
import kg.attractor.xfood.model.Opportunity;
import kg.attractor.xfood.model.User;
import kg.attractor.xfood.repository.OpportunityRepository;
import kg.attractor.xfood.service.OpportunityService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
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
    public List<OpportunityShowDto> getOppotunitiesByDate(LocalDate date) {
        List<Opportunity> opportunities = opportunityRepository.findByDateOrderByUser_SurnameAsc(date);

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

    private OpportunityShowDto createDailyOpportunityDto(User user, LocalDate date) {
        OpportunityShowDto dto = new OpportunityShowDto();
        List<DailyOpportunityShowDto> shifts = new ArrayList<>();

//        List<Opportunity> expertsOpportunities = opportunityRepository.findByUser_IdAndDateOrderByStartTimeAsc(user.getId(), date);

        dto.setUser(dtoBuilder.buildExpertShowDto(user));
//        expertsOpportunities.forEach(e -> {
//            DailyOpportunityShowDto shift = new DailyOpportunityShowDto();
//            shift.setDate(e.getDate());
////            shift.setStartTime(e.getStartTime());
////            shift.setEndTime(e.getEndTime());
//            shifts.add(shift);
//        });
        dto.setShifts(shifts);
        return dto;
    }

    @Override
    public Map<String, List<OpportunityDto>> getAllByExpert() {
        String expertEmail = AuthParams.getPrincipal().getUsername();

        int dayOfWeek = LocalDateTime.now().getDayOfWeek().getValue();
        LocalDate monday = LocalDate.now().minusDays(dayOfWeek - 1);
        LocalDate sunday = LocalDate.now().plusDays(7 - dayOfWeek);

        List<Opportunity> models = opportunityRepository.findAllByUserEmailAndDateBetween(expertEmail, monday, sunday);

        Map<String, List<OpportunityDto>> result = new TreeMap<>();

        for (LocalDate date = monday;
             date.isBefore(sunday) || date.isEqual(sunday);
             date = date.plusDays(1)) {
            result.put(date.toString(), new ArrayList<>());
        }

        models.forEach(opportunity ->
            result.get(opportunity.getDate().toString()).add(dtoBuilder.buildOpportunityDto(opportunity))
        );

        return result;
    }

    @Override
    public List<OpportunityDto> getAllByExpertAndDate(String expertEmail, LocalDate date) {
        return opportunityRepository.findAllByUserEmailAndDate(expertEmail, date)
                .stream()
                .map(dtoBuilder::buildOpportunityDto)
                .toList();
    }

    @Override
    @Transactional
    public void changeExpertOpportunities (OpportunityCreateWrapper wrapper, Authentication auth) {

        User expert = userService.getByEmail(auth.getName());

        opportunityRepository.deleteAllByUserEmailAndDate(expert.getEmail(), wrapper.getDate());
        if (wrapper.getOpportunities() != null) {
            List<Opportunity> opportunities = wrapper.getOpportunities().stream()
                    .map(dto -> modelBuilder.buildNewOpportunity(dto, wrapper.getDate(), expert))
//                    .sorted(Comparator.comparing(Opportunity::getStartTime))
                    .toList();

            for (int i = 0; i < opportunities.size(); i++) {
//                if (opportunities.get(i).getStartTime().isAfter(opportunities.get(i).getEndTime())) {
//                    throw new IllegalArgumentException("Некорректное время");
//                }
//
//                if (i > 0) {
//                    if (opportunities.get(i-1).getEndTime().isAfter(opportunities.get(i).getStartTime())) {
//                        throw new ShiftIntersectionException("Смены не могут пересекаться");
//                    }
//                }

                Opportunity opportunity = opportunities.get(i);
                opportunityRepository.save(opportunity);
            }
        }
    }
}
