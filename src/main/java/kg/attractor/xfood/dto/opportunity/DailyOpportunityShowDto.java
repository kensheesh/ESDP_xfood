package kg.attractor.xfood.dto.opportunity;

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
public class DailyOpportunityShowDto {
    private LocalDateTime date;
    private LocalTime startTime;
    private LocalTime endTime;
}
