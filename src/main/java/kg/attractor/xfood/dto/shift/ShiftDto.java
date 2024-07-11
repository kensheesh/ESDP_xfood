package kg.attractor.xfood.dto.shift;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShiftDto {
    private Long id;
    private LocalTime startTime;
    private LocalTime endTime;
}
