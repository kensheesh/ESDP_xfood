package kg.attractor.xfood.service.impl;

import kg.attractor.xfood.dto.CountryDto;
import kg.attractor.xfood.model.Country;
import kg.attractor.xfood.repository.CountryRepository;
import kg.attractor.xfood.service.CountryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CountryServiceImpl implements CountryService {
	private final CountryRepository countryRepository;
	private final ModelBuilder modelBuilder;
	private final DtoBuilder dtoBuilder;
	
	@Override
	public void add(CountryDto dto) {
		countryRepository.save(modelBuilder.buildCountry(dto));
	}
	
	@Modifying
	@Override
	public void edit(CountryDto dto) {
		countryRepository.save(modelBuilder.buildCountry(dto));
	}
	
	@Override
	public List<CountryDto> getCountries() {
		return countryRepository.findAll()
				.stream()
				.map(dtoBuilder :: buildCountryDto)
				.toList();
	}
	
	protected List<String> getDistinctCountryCodes() {
		return countryRepository.findDistinctCountryCodes()
				.stream()
				.map(Country :: getCountryCode)
				.toList();
	}
}
