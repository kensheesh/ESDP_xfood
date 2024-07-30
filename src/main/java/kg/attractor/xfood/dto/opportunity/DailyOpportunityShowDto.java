package kg.attractor.xfood.dto.opportunity;

import kg.attractor.xfood.dto.shift.ShiftDto;
import kg.attractor.xfood.dto.shift.ShiftTimeShowDto;
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
public class DailyOpportunityShowDto {
    private Long id;
    private boolean isDayOff;
    private String strDate;
    private LocalDate date;
    private List<ShiftTimeShowDto> shifts;
    private boolean isEmpty;
}
