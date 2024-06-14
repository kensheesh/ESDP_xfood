package kg.attractor.xfood.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Value;

import java.io.Serializable;
import java.time.LocalTime;

@Value
@Builder
@AllArgsConstructor
public class WorkScheduleSupervisorShowDto{
    LocalTime startTime;
    LocalTime endTime;
    Long pizzeriaId;
}