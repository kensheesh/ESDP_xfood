package kg.attractor.xfood.service.impl;

import kg.attractor.xfood.dto.location.LocationShowDto;
import kg.attractor.xfood.model.Location;
import kg.attractor.xfood.repository.LocationRepository;
import kg.attractor.xfood.service.LocationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class LocationServiceImpl implements LocationService {
    private final LocationRepository locationRepository;
    private final DtoBuilder dtoBuilder;

    public List<LocationShowDto> getLocations() {
        List<Location> locations = locationRepository.findAll();
        return dtoBuilder.buildLocationShowDtos(locations);
    }
}
