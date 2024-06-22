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
public class PizzeriasShowDodoIsDto {
	@JsonProperty("OrganizationName")
	private String organizationName;
	@JsonProperty("UUId")
	private String uuid;
	@JsonProperty("Name")
	private String name;
	@JsonProperty("Alias")
	private String alias;
	@JsonProperty("TranslitAlias")
	private String alternativeAlias;
	@JsonProperty("Address")
	private String address;
	@JsonProperty("AddressText")
	private String fullAddress;
	@JsonProperty("Orientation")
	private String orientation;
	
/* пример
			"OrganizationName": "\"КРАСТ СПБ\"",
			"UUId": "000D3A29FF6BA94311E87467527E6D6F",
			"Name": "Санкт-Петербург 1-5",
			"Alias": "пр-т. Большевиков",
			"TranslitAlias": "bolshevikov2",
			"Address": "пр-т Большевиков, 2",
			"AddressText": "пр-т. Большевиков (пр-т Большевиков, 2)",
			"Orientation": "В 200 метрах от станции метро Проспект Большевиков",
 */
}
