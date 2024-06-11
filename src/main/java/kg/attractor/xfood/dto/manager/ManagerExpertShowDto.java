package kg.attractor.xfood.dto.manager;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ManagerExpertShowDto {
	private String name;
	private String surname;
}
