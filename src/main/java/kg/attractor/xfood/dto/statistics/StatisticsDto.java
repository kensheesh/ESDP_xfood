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
public class StatisticsDto {
    private List<TableDto> tables;
    private Integer average;
    private List<DayDto> days;
    List<String> pizzerias;
}
