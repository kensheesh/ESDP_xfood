package kg.attractor.xfood.dto.opportunity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DailyOpportunityShowDto {
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
}
