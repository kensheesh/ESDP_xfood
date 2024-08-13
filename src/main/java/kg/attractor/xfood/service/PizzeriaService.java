package kg.attractor.xfood.service;

import kg.attractor.xfood.dto.pizzeria.PizzeriaDto;
import kg.attractor.xfood.dto.pizzeria.PizzeriaShowDto;
import kg.attractor.xfood.dto.pizzeria.PizzeriaWeeklyDto;

import java.util.List;

public interface PizzeriaService {
	
	List<PizzeriaWeeklyDto> getPizzeriasByLocationId(long locationId);
	
	PizzeriaDto getPizzeriaDtoById(Long id);
	
	List<PizzeriaDto> getAllPizzerias();

    List<PizzeriaShowDto> getPizzeriasByPartOfName(String searchQuery);
	
	void add(PizzeriaDto dto);
	
	void edit(PizzeriaDto dto);
}
