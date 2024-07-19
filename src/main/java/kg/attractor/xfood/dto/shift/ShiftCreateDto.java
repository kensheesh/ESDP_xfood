package kg.attractor.xfood.dto.shift;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShiftCreateDto {
    private Long id;
    @Min(0) @Max(23)
    private Integer startTimeHour;
    @Min(0) @Max(59)
    private Integer startTimeMinute;
    @Min(0) @Max(23)
    private Integer endTimeHour;
    @Min(0) @Max(59)
    private Integer endTimeMinute;
}
