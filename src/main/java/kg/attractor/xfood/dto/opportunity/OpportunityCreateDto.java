package kg.attractor.xfood.dto.opportunity;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import kg.attractor.xfood.dto.shift.ShiftDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OpportunityCreateDto {
    private Long id;
    @NotNull
    private LocalDate date;
    private Boolean isDayOff;
    @Valid
    private List<ShiftDto> shifts;
}
