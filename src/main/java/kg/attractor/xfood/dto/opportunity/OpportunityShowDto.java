package kg.attractor.xfood.dto.opportunity;

import kg.attractor.xfood.dto.expert.ExpertShowDto;
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
public class OpportunityShowDto {
    private ExpertShowDto user;
    private LocalDate date;
    private List<ShiftTimeShowDto> shifts;
}
