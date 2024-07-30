package kg.attractor.xfood.dto.statistics;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TableDto {
    private Integer averageByTable;
    private String type;
    private String pizzeria;
    private List<RowDto> rows;
}
