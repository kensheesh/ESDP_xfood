package kg.attractor.xfood.dto.okhttp;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PizzeriaStaffMemberDto {
	@JsonProperty("firstName")
	private String firstName;
	@JsonProperty("lastName")
	private String lastName;
	@JsonProperty("patronymicName")
	private String patronymicName;
	@JsonProperty("phoneNumber")
	private String phoneNumber;
	@JsonProperty("id")
	private String uuid;
}
