package kg.attractor.xfood.service;

import kg.attractor.xfood.dto.CountryDto;

import java.util.List;

public interface CountryService {
	
	void add(CountryDto dto);
	
	void edit(CountryDto dto);
	
	List<CountryDto> getCountries();
}
