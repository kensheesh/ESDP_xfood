package kg.attractor.xfood.dto.workSchedule;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WeekDto {
    private long weekOrder;
    private String monday;
    private String sunday;
}
