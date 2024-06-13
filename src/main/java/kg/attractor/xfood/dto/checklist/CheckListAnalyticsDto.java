package kg.attractor.xfood.dto.checklist;

import kg.attractor.xfood.model.Manager;
import kg.attractor.xfood.model.Pizzeria;
import kg.attractor.xfood.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CheckListAnalyticsDto {
    private Long id;
    private Pizzeria pizzeria;
    private Manager manager;
    private User expert;
    private LocalDate date;
    private int result;
}
