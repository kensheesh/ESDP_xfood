package kg.attractor.xfood.service.impl;

import kg.attractor.xfood.dto.workSchedule.DailyWorkScheduleShowDto;
import kg.attractor.xfood.dto.workSchedule.WeeklyScheduleShowDto;
import kg.attractor.xfood.exception.NotFoundException;
import kg.attractor.xfood.model.Manager;
import kg.attractor.xfood.model.WorkSchedule;
import kg.attractor.xfood.repository.WorkScheduleRepository;
import kg.attractor.xfood.service.WorkScheduleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class WorkScheduleServiceImpl implements WorkScheduleService {
    private final WorkScheduleRepository workScheduleRepository;
    private final DtoBuilder dtoBuilder;


    @Override
    public List<WeeklyScheduleShowDto> getWeeklySchedulesByPizzeriaId(long pizzeriaId) {
        LocalDateTime today = LocalDateTime.now();
        LocalDateTime monday = today.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));

        List<WorkSchedule> workSchedulesOfWeek =
                workScheduleRepository.findByPizzeria_IdAndDateBetween(pizzeriaId, monday, monday.plusDays(6));

        Set<Manager> managersOfPizzeria = new HashSet<>();
        workSchedulesOfWeek.forEach(e -> {
            managersOfPizzeria.add(e.getManager());
        });

        List<WeeklyScheduleShowDto> weeklyDtos = new ArrayList<>();

        managersOfPizzeria.forEach(e -> {
            weeklyDtos.add(createWeeklySchedule(e, monday));
        });

        return weeklyDtos;
    }

    @Override
    public Long getPizzeriaId(Long managerId, LocalDateTime date) {
        WorkSchedule workSchedule = workScheduleRepository.findByManagerIdAndDate(
                        managerId, date.getYear(), date.getMonthValue(), date.getDayOfMonth())
                .orElseThrow(() -> new NotFoundException("No such work_schedule"));
        return workSchedule.getPizzeria().getId();
    }

    public WeeklyScheduleShowDto createWeeklySchedule(Manager manager, LocalDateTime monday) {
        WeeklyScheduleShowDto dto = new WeeklyScheduleShowDto();
        List<DailyWorkScheduleShowDto> managerSchedules = new ArrayList<>();

        for (LocalDateTime dayOfWeek = monday; dayOfWeek.isBefore(monday.plusDays(7)); dayOfWeek = dayOfWeek.plusDays(1)) {
            DailyWorkScheduleShowDto shift = new DailyWorkScheduleShowDto();
            Optional<WorkSchedule> schedule = workScheduleRepository.findByManagerAndDate(manager, dayOfWeek.toLocalDate().atStartOfDay());
            log.info("Optional schedule: " + schedule);
            if (schedule.isPresent()){
                shift.setId(schedule.get().getId());
                shift.setDate(dayOfWeek);
                shift.setWorkDay(true);
                shift.setStartTime(schedule.get().getStartTime());
                shift.setEndTime(schedule.get().getEndTime());
                log.info("Shift: " + shift);
            } else {
                shift.setDate(dayOfWeek);
                shift.setWorkDay(false);
                log.info("Shift: " + shift);
            }
            managerSchedules.add(shift);
        }
        dto.setManager(dtoBuilder.buildManagerShowDto(manager));
        dto.setWeeklySchedule(managerSchedules);
        return dto;
    }
}
