package kg.attractor.xfood.dto.workSchedule;

import kg.attractor.xfood.dto.manager.ManagerShowDto;
import kg.attractor.xfood.dto.pizzeria.PizzeriaDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WeeklyScheduleShowDto {
    private ManagerShowDto manager;
    private List<DailyWorkScheduleShowDto> weeklySchedule;
    private PizzeriaDto pizzeriaDto;
}
