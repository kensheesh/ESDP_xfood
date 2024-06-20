package kg.attractor.xfood.service.impl;

import kg.attractor.xfood.dto.WorkScheduleSupervisorShowDto;
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
                workScheduleRepository.findByPizzeria_IdAndStartTimeBetweenOrderByManager_SurnameAsc(pizzeriaId, monday.toLocalDate(), monday.plusDays(6).toLocalDate());

        Set<Manager> managersOfPizzeria = new HashSet<>();
        workSchedulesOfWeek.forEach(e -> {
            managersOfPizzeria.add(e.getManager());
        });

        List<WeeklyScheduleShowDto> weeklyDtos = new ArrayList<>();

        managersOfPizzeria.forEach(e -> {
            weeklyDtos.add(createWeeklySchedule(e, pizzeriaId, monday));
        });

        return weeklyDtos;
    }


    @Override
    public WorkSchedule findWorkScheduleByManagerAndDate(Long managerId, LocalDateTime startTime, LocalDateTime endTime) {
        return workScheduleRepository.findByManager_IdAndStartTimeAndEndTime(
                        managerId, startTime, endTime)
                .orElseThrow(() -> new NotFoundException("No such work_schedule"));
    }

    @Override
    public WorkScheduleSupervisorShowDto getWorkSchedule(Long managerId, LocalDateTime startTime, LocalDateTime endTime) {
        return dtoBuilder.buildWorkScheduleShowDto(findWorkScheduleByManagerAndDate(managerId, startTime, endTime));
    }

   public WeeklyScheduleShowDto createWeeklySchedule(Manager manager, Long pizzeriaId, LocalDateTime monday) {
        WeeklyScheduleShowDto dto = new WeeklyScheduleShowDto();
        List<DailyWorkScheduleShowDto> managerSchedules = new ArrayList<>();

        for (LocalDateTime dayOfWeek = monday; dayOfWeek.isBefore(monday.plusDays(7)); dayOfWeek = dayOfWeek.plusDays(1)) {
            DailyWorkScheduleShowDto shift = new DailyWorkScheduleShowDto();
            Optional<WorkSchedule> schedule = workScheduleRepository.findByManager_IdAndPizzeria_IdAndStartTime(manager.getId(), pizzeriaId, dayOfWeek.toLocalDate());
            log.info("Optional schedule: " + schedule);
            if (schedule.isPresent()){
                shift.setId(schedule.get().getId());
                shift.setWorkDay(true);
                //ToDo добавить форматтеры дат, но тогда вопрос как передавать дату, для выборки возможностей экспертов, при нажатии на дату графика менеджера
                shift.setDate(schedule.get().getStartTime().toLocalDate().toString());
                shift.setStartTime(schedule.get().getStartTime().toLocalTime().toString());
                shift.setEndTime(schedule.get().getEndTime().toLocalTime().toString());
                log.info("Shift: " + shift);
            } else {
                shift.setDate(dayOfWeek.toLocalDate().toString());
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
