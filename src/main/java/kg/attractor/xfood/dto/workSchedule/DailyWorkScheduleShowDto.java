package kg.attractor.xfood.dto.workSchedule;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DailyWorkScheduleShowDto {
    private Long id;
    private LocalDateTime date;
    private boolean isWorkDay;
    private LocalTime startTime;
    private LocalTime endTime;
}
