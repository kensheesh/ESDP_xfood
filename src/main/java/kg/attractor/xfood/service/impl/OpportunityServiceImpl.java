package kg.attractor.xfood.service.impl;

import jakarta.transaction.Transactional;
import kg.attractor.xfood.AuthParams;
import kg.attractor.xfood.dto.opportunity.DailyOpportunityShowDto;
import kg.attractor.xfood.dto.opportunity.OpportunityCreateDto;
import kg.attractor.xfood.dto.opportunity.OpportunityDto;
import kg.attractor.xfood.dto.opportunity.OpportunityShowDto;
import kg.attractor.xfood.dto.shift.ShiftCreateDto;
import kg.attractor.xfood.exception.NotFoundException;
import kg.attractor.xfood.exception.ShiftIntersectionException;
import kg.attractor.xfood.model.Opportunity;
import kg.attractor.xfood.model.Shift;
import kg.attractor.xfood.model.User;
import kg.attractor.xfood.repository.OpportunityRepository;
import kg.attractor.xfood.service.OpportunityService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
    private final ShiftServiceImpl shiftService;

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
    public Map<String, OpportunityDto> getAllByExpert() {
        String expertEmail = AuthParams.getPrincipal().getUsername();

        int dayOfWeek = LocalDateTime.now().getDayOfWeek().getValue();
        LocalDate monday = LocalDate.now().minusDays(dayOfWeek - 1);
        LocalDate sunday = LocalDate.now().plusDays(7 - dayOfWeek);

        List<Opportunity> models = opportunityRepository.findAllByUserEmailAndDateBetween(expertEmail, monday, sunday);

        Map<String, OpportunityDto> result = new TreeMap<>();

        for (LocalDate date = monday;
             date.isBefore(sunday) || date.isEqual(sunday);
             date = date.plusDays(1)) {
            result.put(date.toString(), null);
        }

        models.forEach(opportunity ->
            result.put(opportunity.getDate().toString(), dtoBuilder.buildOpportunityDto(opportunity))
        );

        return result;
    }

    @Override
    public OpportunityDto getByExpertAndDate(LocalDate date) {
        String expertEmail = AuthParams.getAuth().getName();

        return dtoBuilder.buildOpportunityDto(
                opportunityRepository.findByUserEmailAndDate(expertEmail, date)
                        .orElseThrow(() -> new NotFoundException("Opportunity not found"))
        );
    }

    @Override
    @Transactional
    public void changeExpertOpportunities (OpportunityCreateDto dto) {
        User expert = userService.getByEmail(AuthParams.getAuth().getName());

        Opportunity newOpportunity = Opportunity.builder()
                .id(dto.getId())
                .user(expert)
                .date(dto.getDate())
                .isDayOff(dto.getIsDayOff() != null ? dto.getIsDayOff() : false)
                .build();

        Opportunity savedOpportunity = opportunityRepository.save(newOpportunity);

        if (savedOpportunity.getIsDayOff()) {
            shiftService.deleteAllByOpportunityId(savedOpportunity.getId());
        } else {

            List<Long> shiftsIds;
            List<Long> existingIds = shiftService.getAllByOpportunityId (savedOpportunity.getId());


            if (dto.getShifts() != null) {
                shiftsIds = dto.getShifts().stream()
                        .map(ShiftCreateDto::getId)
                        .toList();

                List<Shift> filteredShifts = dto.getShifts().stream()
                        .filter(shiftDto -> !existingIds.contains(shiftDto.getId()))
                        .map(shiftCreateDto -> modelBuilder.buildNewShift(shiftCreateDto, savedOpportunity))
                        .sorted(Comparator.comparing(Shift::getStartTime))
                        .toList();

                for (int i = 0; i < filteredShifts.size(); i++) {
                    if (filteredShifts.get(i).getStartTime().isAfter(filteredShifts.get(i).getEndTime())) {
                        throw new IllegalArgumentException("Некорректное время");
                    }

                    if (i > 0) {
                        if (filteredShifts.get(i-1).getEndTime().isAfter(filteredShifts.get(i).getStartTime())) {
                            throw new ShiftIntersectionException("Смены не могут пересекаться");
                        }
                    }
                }
                shiftService.saveAll (filteredShifts);
            } else {
                shiftsIds = new ArrayList<>();
            }

            List<Long> idsToDelete = existingIds.stream()
                    .filter(id -> !shiftsIds.contains(id))
                    .toList();

            shiftService.deleteAllByIds(idsToDelete);
        }
    }
}
