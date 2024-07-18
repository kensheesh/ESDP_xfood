package kg.attractor.xfood.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;
@NoArgsConstructor
@Data
@Builder
@AllArgsConstructor
public class WorkScheduleSupervisorShowDto{
    LocalTime startTime;
    LocalTime endTime;
    Long pizzeriaId;
}