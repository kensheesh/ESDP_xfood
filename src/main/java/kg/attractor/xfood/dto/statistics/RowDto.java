package kg.attractor.xfood.dto.statistics;

import kg.attractor.xfood.dto.expert.ExpertShowDto;
import kg.attractor.xfood.dto.manager.ManagerDto;
import kg.attractor.xfood.dto.pizzeria.PizzeriaDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RowDto {
    private List<CellDto> cells;
    private Integer averageByRow;
    private PizzeriaDto pizzeria;
    private ManagerDto manager;
    private ExpertShowDto expert;
    private String type;
}
