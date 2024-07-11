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
    @NotNull
    @Min(0) @Max(23)
    private Integer startTimeHour;
    @NotNull
    @Min(0) @Max(59)
    private Integer startTimeMinute;
    @NotNull
    @Min(0) @Max(23)
    private Integer endTimeHour;
    @NotNull
    @Min(0) @Max(59)
    private Integer endTimeMinute;
}
