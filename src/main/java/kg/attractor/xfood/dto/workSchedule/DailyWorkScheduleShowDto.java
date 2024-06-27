package kg.attractor.xfood.dto.workSchedule;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DailyWorkScheduleShowDto {
    private Long id;
    private boolean isWorkDay;
    private String date;
    private String startTime;
    private String endTime;
}
