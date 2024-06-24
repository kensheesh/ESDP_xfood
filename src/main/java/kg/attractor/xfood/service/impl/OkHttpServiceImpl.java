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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;


@Slf4j
@Service
@RequiredArgsConstructor
public class OkHttpServiceImpl implements OkHttpService {
	
	private static final String PIZZERIA_CACHE_KEY = "pizzerias";
	private static final MediaType JSON = MediaType.APPLICATION_JSON;
	private static final String API_URL = "https://api.dodois.io";
	
	@Autowired
	private RedisTemplate<String, Object> redisTemplate;
	private final OkHttpClient client = new OkHttpClient();
	private final ObjectMapper objectMapper = new ObjectMapper();
	
	@Override
	public List<PizzeriasShowDodoIsDto> getPizzeriasByMatch(String s) {
		List<Object> objects = redisTemplate.opsForList().range(PIZZERIA_CACHE_KEY, 0, - 1);
		List<PizzeriasShowDodoIsDto> pizzerias = new ArrayList<>();
		
		if (objects != null) {
			objects.stream()
					.filter(obj -> obj instanceof LinkedHashMap)
					.forEach(obj -> pizzerias.add(convertMapToDto((LinkedHashMap<String, Object>) obj)));
		} else {
			log.warn("No pizzerias found in Redis cache");
		}
		
		return pizzerias.stream()
				.filter(e -> e.toString()
						.toLowerCase()
						.contains(s.toLowerCase()))
				.toList();
	}
	
	@Override
	public void rewritePizzeriasToRedis(List<String> countryCodes) {
		redisTemplate.delete(PIZZERIA_CACHE_KEY);
		log.info("Deleted all pizzerias from Redis");
		
		countryCodes.forEach(countryCode -> {
			List<PizzeriasShowDodoIsDto> dtos = getAllPizzeriasByCountry(countryCode);
			
			dtos.forEach(dto -> redisTemplate
					.opsForList()
					.rightPush(PIZZERIA_CACHE_KEY, dto));
			
			log.info("Saved {} pizzerias to Redis for country code: {}", dtos.size(), countryCode);
		});
	}
	
	private List<PizzeriasShowDodoIsDto> getAllPizzeriasByCountry(String countryCode) {
		String json = publicApiRunner(getAllPizzeriasByCountryUrl(countryCode));
		List<PizzeriasShowDodoIsDto> dtos = new ArrayList<>();
		
		if (json != null) {
			try {
				JsonNode jsonNode = objectMapper.readTree(json);
				for (JsonNode node : jsonNode) {
					dtos.add(buildDtoFromJson(node, countryCode));
				}
			} catch (IOException e) {
				log.error("Error parsing JSON response: {}", e.getMessage());
			}
		}
		return dtos;
	}
	
	//Execute a GET request to a public API.
	private String publicApiRunner(String url) {
		Request request = new Request.Builder()
				.url(url)
				.get()
				.addHeader("Accept", JSON.toString())
				.build();
		
		try (Response response = client.newCall(request).execute()) {
			if (! response.isSuccessful()) {
				log.error("Unexpected response code: {}", response.code());
				throw new IOException("Unexpected response code " + response);
			}
			return response.body().string();
		} catch (IOException e) {
			log.error("Error calling public API: {}", e.getMessage());
			return null;
		}
	}
	
	//Execute a GET request to a secured API.
	private String authApiRunner(String url, String bearerToken) {
		log.info("Making authorized GET request to URL: {}", url);
		
		Request request = new Request.Builder()
				.url(url)
				.get()
				.addHeader("Accept", JSON.toString())
				.addHeader("Authorization", "Bearer " + bearerToken)
				.build();
		
		try (Response response = client.newCall(request).execute()) {
			if (! response.isSuccessful()) {
				log.error("Request to URL: {} failed with status code: {}", url, response.code());
				throw new IOException("Unexpected response code " + response.code());
			}
			log.info("Request to URL: {} succeeded with status code: {}", url, response.code());
			return response.body().string();
		} catch (IOException e) {
			log.error("Error executing request to URL: {}: {}", url, e.getMessage());
			return null;
		}
	}
	
	//public
	private String getAllPizzeriasByCountryUrl(String countryCode) {
		return String.format("https://publicapi.dodois.io/%s/api/v1/unitinfo/all", countryCode);
	}
	
	//need token
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
	
	// MAPPERS/BUILDERS
	private PizzeriasShowDodoIsDto buildDtoFromJson(JsonNode jsonNode, String countryCode) {
		return PizzeriasShowDodoIsDto.builder().organizationName(jsonNode.get("OrganizationName").asText()).uuid(jsonNode.get("UUId").asText()).name(jsonNode.get("Name").asText()).alias(jsonNode.get("Alias").asText()).translitAlias(jsonNode.get("TranslitAlias").asText()).address(jsonNode.get("Address").asText()).addressText(jsonNode.get("AddressText").asText()).orientation(jsonNode.get("Orientation").asText()).countryCode(countryCode).build();
	}
	
	private PizzeriasShowDodoIsDto convertMapToDto(LinkedHashMap<String, Object> map) {
		return objectMapper.convertValue(map, PizzeriasShowDodoIsDto.class);
	}
	
	
/* пока не нужны
	private final String AUTH_URL = "https://auth.dodois.io";
	private final String REDIRECT_URL = "https://localhost:5001";
	private final Dotenv dotenv = Dotenv.load();
*/
}
