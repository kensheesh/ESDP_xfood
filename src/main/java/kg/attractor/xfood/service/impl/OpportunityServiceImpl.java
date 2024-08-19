package kg.attractor.xfood.service.impl;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import kg.attractor.xfood.AuthParams;
import kg.attractor.xfood.dto.opportunity.DailyOpportunityShowDto;
import kg.attractor.xfood.dto.opportunity.OpportunityCreateDto;
import kg.attractor.xfood.dto.opportunity.OpportunityDto;
import kg.attractor.xfood.dto.opportunity.OpportunityShowDto;
import kg.attractor.xfood.dto.opportunity.WeeklyOpportunityShowDto;
import kg.attractor.xfood.dto.shift.ShiftCreateDto;
import kg.attractor.xfood.dto.workSchedule.DailyWorkScheduleShowDto;
import kg.attractor.xfood.dto.workSchedule.WeeklyScheduleShowDto;
import kg.attractor.xfood.exception.NotFoundException;
import kg.attractor.xfood.exception.ShiftIntersectionException;
import kg.attractor.xfood.model.Manager;
import kg.attractor.xfood.model.Opportunity;
import kg.attractor.xfood.model.Shift;
import kg.attractor.xfood.model.User;
import kg.attractor.xfood.model.WorkSchedule;
import kg.attractor.xfood.repository.OpportunityRepository;
import kg.attractor.xfood.service.OpportunityService;
import kg.attractor.xfood.service.SettingService;
import kg.attractor.xfood.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
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
    private final SettingService settingService;

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

    private OpportunityShowDto createDailyOpportunityDto(User user, LocalDate date) {
        OpportunityShowDto dto = new OpportunityShowDto();
        Optional<Opportunity> expertOpportunity = opportunityRepository.findByUser_IdAndDate(user.getId(), date);
        if (expertOpportunity.isPresent()) {
            List<Shift> shifts = shiftService.getShiftsByOpportunityId(expertOpportunity.get().getId());
            dto.setUser(dtoBuilder.buildExpertShowDto(user));
            dto.setDate(date);
            dto.setShifts(dtoBuilder.buildShiftTimeShowDtos(shifts));
            return dto;
        } else {
            return null;
        }
    }

    @Override
    public Long save(Opportunity opportunity) {
        Long id =  opportunityRepository.save(opportunity).getId();
        log.info("opportunity saved {}", opportunity);
        return id;
    }

    @Override
    public Map<String, OpportunityDto> getAllByExpert(int week) {
        String expertEmail = AuthParams.getPrincipal().getUsername();

        int dayOfWeek = LocalDateTime.now().getDayOfWeek().getValue();
        LocalDate monday = LocalDate.now().plusWeeks(week).minusDays(dayOfWeek - 1);
        LocalDate sunday = LocalDate.now().plusWeeks(week).plusDays(7 - dayOfWeek);

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
    public void changeExpertOpportunity(OpportunityCreateDto dto) {
        int dayOfWeek = dto.getDate().getDayOfWeek().getValue();
        if (!settingService.isAvailableToChange(dto.getDate().minusDays(dayOfWeek - 1))) {
            throw new IllegalArgumentException("You cannot change shifts of this week!");
        }
//        To get the week number relative to the current week
        LocalDate today = LocalDate.now();
        LocalDate startOfWeekToday = today.with(java.time.DayOfWeek.MONDAY);
        LocalDate startOfWeekGivenDate = dto.getDate().with(java.time.DayOfWeek.MONDAY);
        int week = (int) ChronoUnit.WEEKS.between(startOfWeekToday, startOfWeekGivenDate);

        if (!settingService.isAvailableToDayOff(getAllByExpert(week)) && dto.getIsDayOff() != null && dto.getIsDayOff()) {
            throw new IllegalArgumentException("You cannot add extra day offs");
        }

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
            List<Long> existingIds = shiftService.getAllByOpportunityId(savedOpportunity.getId());

            if (dto.getShifts() != null) {
                shiftsIds = dto.getShifts().stream()
                        .map(ShiftCreateDto::getId)
                        .toList();

                List<Shift> filteredShifts = dto.getShifts().stream()
                        .filter(shiftDto -> !existingIds.contains(shiftDto.getId()) &&
                                shiftDto.getStartTimeHour() != null &&
                                shiftDto.getEndTimeHour() != null)
                        .map(shiftCreateDto -> modelBuilder.buildNewShift(shiftCreateDto, savedOpportunity))
                        .sorted(Comparator.comparing(Shift::getStartTime))
                        .toList();

                if (filteredShifts.size() > 5) {
                    throw new IllegalArgumentException("Максимальное кол-во смен: 5");
                }

                for (int i = 0; i < filteredShifts.size(); i++) {

                    if (filteredShifts.get(i).getStartTime().isAfter(filteredShifts.get(i).getEndTime())
                    || filteredShifts.get(i).getStartTime().equals(filteredShifts.get(i).getEndTime())) {
                        throw new IllegalArgumentException("Некорректное время");
                    }

                    if (i > 0) {
                        if (filteredShifts.get(i - 1).getEndTime().isAfter(filteredShifts.get(i).getStartTime())) {
                            throw new ShiftIntersectionException("Смены не могут пересекаться");
                        }
                    }
                }
                shiftService.saveAll(filteredShifts);
            } else {
                shiftsIds = new ArrayList<>();
            }

            List<Long> idsToDelete = existingIds.stream()
                    .filter(id -> !shiftsIds.contains(id))
                    .toList();

            shiftService.deleteAllByIds(idsToDelete);
        }
    }

    @Override
    public List<WeeklyOpportunityShowDto> getWeeklyOpportunities(long week, String search) {
        LocalDateTime today = LocalDateTime.now();
        LocalDateTime monday = today.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        LocalDateTime chosenMonday = monday.plusDays(7 * week);

        List<Opportunity> opportunitiesOfWeek =
                opportunityRepository.findByDateBetweenOrderByUser_SurnameAsc(chosenMonday.toLocalDate(), chosenMonday.plusDays(6).toLocalDate());

        Set<User> uniqueExperts = new HashSet<>();
        opportunitiesOfWeek.forEach(e -> uniqueExperts.add(e.getUser()));
        List<User> sortedUniqueExperts = new ArrayList<>(uniqueExperts);
        sortedUniqueExperts.sort(Comparator.comparing(User::getSurname));

        List<User> restExperts = userService.getAllExperts();
        restExperts.removeAll(uniqueExperts);
        restExperts.sort(Comparator.comparing(User::getSurname));

        List<WeeklyOpportunityShowDto> weeklyDtos = new ArrayList<>();

        sortedUniqueExperts.forEach(e -> weeklyDtos.add(createWeeklyOpportunity(e, chosenMonday)));
        restExperts.forEach(e -> weeklyDtos.add(createWeeklyOpportunity(e, chosenMonday)));

        return weeklyDtos;
    }

    private WeeklyOpportunityShowDto createWeeklyOpportunity(User e, LocalDateTime monday) {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        WeeklyOpportunityShowDto dto = new WeeklyOpportunityShowDto();
        List<DailyOpportunityShowDto> expertOpportunities = new ArrayList<>();

        for (LocalDateTime dayOfWeek = monday; dayOfWeek.isBefore(monday.plusDays(7)); dayOfWeek = dayOfWeek.plusDays(1)) {
            DailyOpportunityShowDto opportunityDto = new DailyOpportunityShowDto();
            Optional<Opportunity> opportunity = opportunityRepository.findFirstByUser_IdAndDate(e.getId(), dayOfWeek.toLocalDate());
            log.info("Optional opportunity: " + opportunity.toString());
            if (opportunity.isPresent()){
                opportunityDto.setId(opportunity.get().getId());
                opportunityDto.setDate(dayOfWeek.toLocalDate());
                opportunityDto.setStrDate(dayOfWeek.toLocalDate().format(dateFormatter));
                if (opportunity.get().getIsDayOff()) {
                    opportunityDto.setDayOff(true);
                } else {
                    opportunityDto.setDayOff(false);
                    opportunityDto.setShifts(
                            dtoBuilder.buildShiftTimeShowDtos(
                                    shiftService.getShiftsByOpportunityId(
                                            opportunity.get().getId())));
                }
            } else {
                opportunityDto.setDate(dayOfWeek.toLocalDate());
                opportunityDto.setStrDate(dayOfWeek.toLocalDate().format(dateFormatter));
                opportunityDto.setEmpty(true);
            }
            expertOpportunities.add(opportunityDto);
        }
        dto.setExpert(dtoBuilder.buildExpertShowDto(e));
        dto.setWeeklyOpportunity(expertOpportunities);
        return dto;
    }
}
