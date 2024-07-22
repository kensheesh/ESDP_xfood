package kg.attractor.xfood.dto.statistics;

import kg.attractor.xfood.dto.expert.ExpertShowDto;
import kg.attractor.xfood.dto.manager.ManagerShowDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CheckListRowInfoDto {
    private String pizzeria;
    private ManagerShowDto managerShowDto;
    private ExpertShowDto expertShowDto;
    private Integer points;
    private LocalDate checkDate;
}
