package kg.attractor.xfood.dto.settings;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DeadlinesDto {
    @NotNull
    private int oppDeadline;
    @NotNull
    private int dayOff;
    @NotNull
    private int appealDeadline;
}
