package kg.attractor.xfood.dto.opportunity;

import kg.attractor.xfood.dto.shift.ShiftDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OpportunityDto {
    private Long id;
    private Boolean isDayOff;
    List<ShiftDto> shifts;
}
