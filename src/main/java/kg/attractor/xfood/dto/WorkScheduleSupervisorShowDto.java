package kg.attractor.xfood.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;

import java.time.LocalTime;

@Builder
@AllArgsConstructor
public class WorkScheduleSupervisorShowDto{
    LocalTime startTime;
    LocalTime endTime;
    Long pizzeriaId;
}