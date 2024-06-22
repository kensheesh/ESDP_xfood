package kg.attractor.xfood.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import kg.attractor.xfood.dto.okhttp.PizzeriasShowDodoIsDto;
import kg.attractor.xfood.service.OkHttpService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@Slf4j
@Service
@RequiredArgsConstructor
public class OkHttpServiceImpl implements OkHttpService {
	
	public static final MediaType JSON = MediaType.APPLICATION_JSON;
	private final String API_URL = "https://api.dodois.io";
	private final OkHttpClient client = new OkHttpClient();
	private final ObjectMapper objectMapper = new ObjectMapper();
	
	
	@Override
	public List<PizzeriasShowDodoIsDto> getAllPizzerias(String country) {
		String json = publicApiRunner(getAllPizzeriasByCountryUrl(country));
		
		List<PizzeriasShowDodoIsDto> dtos = new ArrayList<>();
		try {
			JsonNode jsonNode = objectMapper.readTree(json);
			for (JsonNode node : jsonNode) {
				PizzeriasShowDodoIsDto dto = buildDtoFromJson(node);
				dtos.add(dto);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return dtos;
	}
	
	public List<PizzeriasShowDodoIsDto> getPizzeriasByName(String contry, String name) {
		
		return null;
	}
	
	//string=json
	private String publicApiRunner(String url) {
		Request request = new Request.Builder()
				.url(url)
				.get()
				.addHeader("Accept", JSON.toString())
				.build();
		try (Response response = client.newCall(request).execute()) {
			if (! response.isSuccessful()) throw new IOException("Unexpected code " + response);
			return response.body().string();
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	private PizzeriasShowDodoIsDto buildDtoFromJson(JsonNode jsonNode) {
		return PizzeriasShowDodoIsDto.builder()
				.organizationName(jsonNode.get("OrganizationName").asText())
				.uuid(jsonNode.get("UUId").asText())
				.name(jsonNode.get("Name").asText())
				.alias(jsonNode.get("Alias").asText())
				.alternativeAlias(jsonNode.get("TranslitAlias").asText())
				.address(jsonNode.get("Address").asText())
				.fullAddress(jsonNode.get("AddressText").asText())
				.orientation(jsonNode.get("Orientation").asText())
				.build();
	}
	
	private String authApiRunner(String url, String BearerToken) {
		Request request = new Request.Builder()
				.url(url)
				.get()
				.addHeader("Accept", JSON.toString())
				.addHeader("Authorization", "Bearer " + BearerToken)
				.build();
		
		try (Response response = client.newCall(request).execute()) {
			if (! response.isSuccessful()) throw new IOException("Unexpected code " + response);
			return response.body().string();
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	//public
	private String getAllPizzeriasByCountryUrl(String countryCode) {
		return String.format("https://publicapi.dodois.io/%s/api/v1/unitinfo/all", countryCode);
	}
	
	//public
	private String getPizzeriasStaffMembersUrl(String countryCode, String pizzeriaUUID) {
		return String.format("%s/dodopizza/%s/staff/members?statuses=Active&units=%s", API_URL, countryCode, pizzeriaUUID);
	}
	
	//need token
	private String getPizzeriasWeeklyShiftsUrl(String countryCode, String pizzeriaUUID, String weekStart, String weekEnd) {
		return String.format("%s/dodopizza/%s/staff/schedules" +
						"?beginFrom=%s" +
						"&beginTo=%s" +
						"&units=%s" +
						"&staffType=KitchenMember",
				API_URL, countryCode, weekStart, weekEnd, pizzeriaUUID);
	}

/* пока не нужны
	private final String AUTH_URL = "https://auth.dodois.io";
	private final String REDIRECT_URL = "https://localhost:5001";
	private final Dotenv dotenv = Dotenv.load();
*/
}
