package kg.attractor.xfood.service;

import kg.attractor.xfood.dto.LocationDto;

import java.util.List;

public interface LocationService {
	List<LocationDto> getAllLocations();
	
	void add(LocationDto dto);
	
	void edit(LocationDto dto);
}
