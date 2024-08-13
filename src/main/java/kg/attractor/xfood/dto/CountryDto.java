package kg.attractor.xfood.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CountryDto {
	private Long id;
	@NotBlank
	private String countryName, countryCode, apiUrl, authUrl;
	
}
