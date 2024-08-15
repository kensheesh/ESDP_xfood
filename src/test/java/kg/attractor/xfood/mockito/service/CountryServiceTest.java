package kg.attractor.xfood.mockito.service;

import kg.attractor.xfood.dto.CountryDto;
import kg.attractor.xfood.model.Country;
import kg.attractor.xfood.repository.CountryRepository;
import kg.attractor.xfood.service.impl.CountryServiceImpl;
import kg.attractor.xfood.service.impl.DtoBuilder;
import kg.attractor.xfood.service.impl.ModelBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CountryServiceTest {

    @InjectMocks
    private CountryServiceImpl countryService;

    @Mock
    private CountryRepository countryRepository;

    @Mock
    private ModelBuilder modelBuilder;

    @Mock
    private DtoBuilder dtoBuilder;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAdd() {
        CountryDto dto = new CountryDto();
        dto.setCountryCode("US");

        Country country = new Country();
        country.setCountryCode("US");

        when(modelBuilder.buildCountry(dto)).thenReturn(country);

        countryService.add(dto);

        verify(modelBuilder, times(1)).buildCountry(dto);
        verify(countryRepository, times(1)).save(country);
    }

    @Test
    void testEdit() {
        CountryDto dto = new CountryDto();
        dto.setCountryCode("US");

        Country country = new Country();
        country.setCountryCode("US");

        when(modelBuilder.buildCountry(dto)).thenReturn(country);

        countryService.edit(dto);

        verify(modelBuilder, times(1)).buildCountry(dto);
        verify(countryRepository, times(1)).save(country);
    }

    @Test
    void testGetCountries() {
        Country country1 = new Country();
        country1.setCountryCode("US");

        Country country2 = new Country();
        country2.setCountryCode("CA");

        List<Country> countries = List.of(country1, country2);

        CountryDto dto1 = new CountryDto();
        dto1.setCountryCode("US");

        CountryDto dto2 = new CountryDto();
        dto2.setCountryCode("CA");

        when(countryRepository.findAll()).thenReturn(countries);
        when(dtoBuilder.buildCountryDto(country1)).thenReturn(dto1);
        when(dtoBuilder.buildCountryDto(country2)).thenReturn(dto2);

        List<CountryDto> result = countryService.getCountries();

        assertNotNull(result);
        assertEquals(2, result.size());

        assertEquals(dto1.getCountryCode(), result.get(0).getCountryCode());

        assertEquals(dto2.getCountryCode(), result.get(1).getCountryCode());

        verify(countryRepository, times(1)).findAll();
        verify(dtoBuilder, times(1)).buildCountryDto(country1);
        verify(dtoBuilder, times(1)).buildCountryDto(country2);
    }

    @Test
    void testGetCountries_EmptyList() {
        when(countryRepository.findAll()).thenReturn(new ArrayList<>());

        List<CountryDto> result = countryService.getCountries();

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(countryRepository, times(1)).findAll();
    }

    @Test
    void testGetDistinctCountryCodes() {
        Country country1 = new Country();
        country1.setCountryCode("US");

        Country country2 = new Country();
        country2.setCountryCode("CA");

        List<Country> countries = List.of(country1, country2);

        when(countryRepository.findDistinctCountryCodes()).thenReturn(countries);

        List<String> result = countryService.getDistinctCountryCodes();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertTrue(result.contains("US"));
        assertTrue(result.contains("CA"));

        verify(countryRepository, times(1)).findDistinctCountryCodes();
    }

    @Test
    void testGetDistinctCountryCodes_EmptyList() {
        when(countryRepository.findDistinctCountryCodes()).thenReturn(new ArrayList<>());

        List<String> result = countryService.getDistinctCountryCodes();

        assertNotNull(result);
        assertTrue(result.isEmpty());

        verify(countryRepository, times(1)).findDistinctCountryCodes();
    }
}