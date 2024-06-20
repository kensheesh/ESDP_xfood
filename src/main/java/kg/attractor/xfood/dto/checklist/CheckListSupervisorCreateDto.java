package kg.attractor.xfood.dto.checklist;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import kg.attractor.xfood.dto.criteria.CriteriaMaxValueDto;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CheckListSupervisorCreateDto {
    private LocalDate date;
    @NotNull
    private LocalTime startTime;
    @NotNull
    private LocalTime endTime;
    private Long managerId;
    private Long expertId;
    @NotNull
    private Long checkTypeId;

    List<@Valid CriteriaMaxValueDto> criteriaMaxValueDtoList ;

}
