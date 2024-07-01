package kg.attractor.xfood.dto.okhttp;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder()
@NoArgsConstructor
@AllArgsConstructor
public class PizzeriasShowDodoIsDto implements Serializable {
	@JsonProperty("countryCode")
	private String countryCode;
	@JsonProperty("OrganizationName")
	private String organizationName;
	@JsonProperty("UUId")
	private String uuid;
	@JsonProperty("Name")
	private String name;
	@JsonProperty("Alias")
	private String alias;
	@JsonProperty("TranslitAlias")
	private String translitAlias;
	@JsonProperty("Address")
	private String address;
	@JsonProperty("AddressText")
	private String addressText;
	@JsonProperty("Orientation")
	private String orientation;
	
}
