package kg.attractor.xfood.service;

import kg.attractor.xfood.dto.okhttp.PizzeriasShowDodoIsDto;

import java.util.List;

public interface OkHttpService {
	
	List<PizzeriasShowDodoIsDto> getPizzeriasByMatch(String name);
	
	void rewritePizzeriasToRedis(List<String> countryCodes);
}
