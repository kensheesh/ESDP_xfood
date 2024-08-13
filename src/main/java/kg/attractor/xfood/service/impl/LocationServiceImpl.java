package kg.attractor.xfood.service.impl;

import kg.attractor.xfood.dto.LocationDto;
import kg.attractor.xfood.dto.location.LocationShowDto;
import kg.attractor.xfood.exception.NotFoundException;
import kg.attractor.xfood.model.Location;
import kg.attractor.xfood.repository.LocationRepository;
import kg.attractor.xfood.service.LocationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Slf4j
@Service
@RequiredArgsConstructor
public class LocationServiceImpl implements LocationService {
    
    private final LocationRepository locationRepository;
    private final DtoBuilder dtoBuilder;
    private final ModelBuilder modelBuilder;

    public List<LocationShowDto> getLocations() {
        List<Location> locations = locationRepository.findAll();
        return dtoBuilder.buildLocationShowDtos(locations);
    }
    
    @Override
    public List<LocationDto> getAllLocations() {
        return locationRepository.findAll()
                .stream()
                .map(dtoBuilder :: buildLocationDto)
                .collect(toList());
    }
    
    @Override
    public void add(LocationDto dto) {
        locationRepository.save(modelBuilder.buildLocation(dto));
    }
    
    @Modifying
    @Override
    public void edit(LocationDto dto) {
        locationRepository.save(modelBuilder.buildLocation(dto));
    }
    
    public Location findLocationById(Long locationId) {
        return locationRepository.findById(locationId)
                .orElseThrow(() -> new NotFoundException("Location Id is invalid"));
    }
}
