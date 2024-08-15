package kg.attractor.xfood.mockito.service;

import kg.attractor.xfood.dto.LocationDto;
import kg.attractor.xfood.dto.location.LocationShowDto;
import kg.attractor.xfood.exception.NotFoundException;
import kg.attractor.xfood.model.Location;
import kg.attractor.xfood.repository.LocationRepository;
import kg.attractor.xfood.service.impl.DtoBuilder;
import kg.attractor.xfood.service.impl.LocationServiceImpl;
import kg.attractor.xfood.service.impl.ModelBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class LocationServiceTest {

    @InjectMocks
    private LocationServiceImpl locationService;

    @Mock
    private LocationRepository locationRepository;

    @Mock
    private DtoBuilder dtoBuilder;

    @Mock
    private ModelBuilder modelBuilder;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    private Location createLocation(Long id, String name) {
        return Location.builder()
                .id(id)
                .name(name)
                .build();
    }

    private LocationDto createLocationDto(Long id, String name) {
        return LocationDto.builder()
                .id(id)
                .name(name)
                .build();
    }

    private LocationShowDto createLocationShowDto(Long id, String name) {
        return LocationShowDto.builder()
                .id(id)
                .name(name)
                .build();
    }

    @Test
    void testGetLocations() {
        List<Location> locations = Arrays.asList(
                createLocation(1L, "Location1"),
                createLocation(2L, "Location2")
        );

        List<LocationShowDto> locationShowDtos = Arrays.asList(
                createLocationShowDto(1L, "Location1"),
                createLocationShowDto(2L, "Location2")
        );

        when(locationRepository.findAll()).thenReturn(locations);
        when(dtoBuilder.buildLocationShowDtos(locations)).thenReturn(locationShowDtos);

        List<LocationShowDto> result = locationService.getLocations();

        assertNotNull(result);
        assertEquals(locationShowDtos.size(), result.size());
        assertEquals(locationShowDtos, result);
    }

    @Test
    void testGetAllLocations() {
        List<Location> locations = Arrays.asList(
                createLocation(1L, "Location1"),
                createLocation(2L, "Location2")
        );

        List<LocationDto> locationDtos = Arrays.asList(
                createLocationDto(1L, "Location1"),
                createLocationDto(2L, "Location2")
        );

        when(locationRepository.findAll()).thenReturn(locations);
        when(dtoBuilder.buildLocationDto(any(Location.class)))
                .thenReturn(locationDtos.get(0))
                .thenReturn(locationDtos.get(1));

        List<LocationDto> result = locationService.getAllLocations();

        assertNotNull(result);
        assertEquals(locationDtos.size(), result.size());
        assertEquals(locationDtos, result);
    }

    @Test
    void testAddLocation() {
        LocationDto dto = createLocationDto(1L, "New Location");
        Location location = createLocation(1L, "New Location");

        when(modelBuilder.buildLocation(dto)).thenReturn(location);

        locationService.add(dto);

        verify(locationRepository).save(location);
    }

    @Test
    void testEditLocation() {
        LocationDto dto = createLocationDto(1L, "Updated Location");
        Location location = createLocation(1L, "Updated Location");

        when(modelBuilder.buildLocation(dto)).thenReturn(location);

        locationService.edit(dto);

        verify(locationRepository).save(location);
    }

    @Test
    void testFindLocationById() {
        Long locationId = 1L;
        Location location = createLocation(locationId, "Location1");

        when(locationRepository.findById(locationId)).thenReturn(Optional.of(location));

        Location result = locationService.findLocationById(locationId);

        assertNotNull(result);
        assertEquals(location, result);
    }

    @Test
    void testFindLocationByIdNotFound() {
        Long locationId = 1L;

        when(locationRepository.findById(locationId)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> locationService.findLocationById(locationId));
    }

    @Test
    void testAddLocationWithInvalidData() {
        LocationDto dto = createLocationDto(null, null); // Invalid data
        Location location = createLocation(null, null);

        when(modelBuilder.buildLocation(dto)).thenReturn(location);

        locationService.add(dto);

        verify(locationRepository).save(location);
    }

    @Test
    void testEditLocationWithInvalidData() {
        LocationDto dto = createLocationDto(null, null); // Invalid data
        Location location = createLocation(null, null);

        when(modelBuilder.buildLocation(dto)).thenReturn(location);

        locationService.edit(dto);

        verify(locationRepository).save(location);
    }

    @Test
    void testGetLocationsEmptyList() {
        when(locationRepository.findAll()).thenReturn(Collections.emptyList());
        when(dtoBuilder.buildLocationShowDtos(Collections.emptyList())).thenReturn(Collections.emptyList());

        List<LocationShowDto> result = locationService.getLocations();

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void testGetAllLocationsEmptyList() {
        when(locationRepository.findAll()).thenReturn(Collections.emptyList());
        when(dtoBuilder.buildLocationDto(any(Location.class))).thenReturn(null);

        List<LocationDto> result = locationService.getAllLocations();

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void testFindLocationByIdMultipleCalls() {
        Long locationId = 1L;
        Location location = createLocation(locationId, "Location1");

        when(locationRepository.findById(locationId)).thenReturn(Optional.of(location));

        Location result1 = locationService.findLocationById(locationId);
        Location result2 = locationService.findLocationById(locationId);

        assertNotNull(result1);
        assertNotNull(result2);
        assertEquals(location, result1);
        assertEquals(location, result2);
    }

    @Test
    void testAddLocationWithExistingId() {
        LocationDto dto = createLocationDto(1L, "Location with Existing Id");
        Location location = createLocation(1L, "Location with Existing Id");

        when(modelBuilder.buildLocation(dto)).thenReturn(location);

        locationService.add(dto);

        verify(locationRepository).save(location);
    }
}