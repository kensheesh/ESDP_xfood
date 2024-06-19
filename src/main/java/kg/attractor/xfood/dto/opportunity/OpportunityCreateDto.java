package kg.attractor.xfood.dto.opportunity;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OpportunityCreateDto {
    private Long id;
    @NotNull
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDate date;
    @NotNull @Max(23)
    private Integer startTimeHour;
    @NotNull @Max(59)
    private Integer startTimeMinute;
    @NotNull @Max(23)
    private Integer endTimeHour;
    @NotNull @Max(59)
    private Integer endTimeMinute;
}
