package kg.attractor.xfood.dto.statistics;

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
public class CheckListStatisticsDto {
    private Integer averagePoints;
    List<CheckListStatisticsDto> checkListStatistics;
    private String type;
    private LocalDate startDate;
    private LocalDate endDate;
}
