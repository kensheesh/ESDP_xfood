package kg.attractor.xfood.dto.workSchedule;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WorkScheduleCreateDto {
    @NotNull
    private long pizzeriaId;
    @NotNull
    private long managerId;
    @NotNull
    private LocalDate currentDate;
    @NotNull
    private long expertId;
    private int startTimeHour;
    private int startTimeMinute;
    private int endTimeHour;
    private int endTimeMinute;
}
