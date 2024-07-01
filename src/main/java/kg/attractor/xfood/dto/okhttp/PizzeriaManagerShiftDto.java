package kg.attractor.xfood.dto.okhttp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PizzeriaManagerShiftDto implements Serializable {
	private LocalDateTime scheduledShiftStartAtLocal;
	private LocalDateTime scheduledShiftEndAtLocal;
	private String staffId;
	private String unitId;
	private String unitName;
	private String name;
	private String surname;
	private String phNumber;
}
