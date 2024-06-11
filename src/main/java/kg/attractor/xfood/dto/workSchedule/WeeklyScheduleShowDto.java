package kg.attractor.xfood.dto.workSchedule;

import kg.attractor.xfood.dto.manager.ManagerExpertShowDto;
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
    private ManagerExpertShowDto manager;
    private List<DailyWorkScheduleShowDto> weeklySchedule;
}
