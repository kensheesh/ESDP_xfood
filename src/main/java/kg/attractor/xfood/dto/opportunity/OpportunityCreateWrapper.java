package kg.attractor.xfood.dto.opportunity;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OpportunityCreateWrapper {
    @Valid
    private List<OpportunityCreateDto> opportunities;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDate date;
}
