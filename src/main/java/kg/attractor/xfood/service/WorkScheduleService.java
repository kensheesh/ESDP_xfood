package kg.attractor.xfood.service;

import kg.attractor.xfood.dto.workSchedule.WeeklyScheduleShowDto;

import java.util.List;

public interface WorkScheduleService {
    List<WeeklyScheduleShowDto> getWeeklySchedulesByPizzeriaId(long pizzeriaId);
}
