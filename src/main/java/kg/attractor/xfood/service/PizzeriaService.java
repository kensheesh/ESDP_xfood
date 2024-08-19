package kg.attractor.xfood.service;

import kg.attractor.xfood.dto.pizzeria.PizzeriaDto;
import kg.attractor.xfood.dto.pizzeria.PizzeriaShowDto;
import kg.attractor.xfood.dto.pizzeria.PizzeriaWeeklyDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PizzeriaService {
	
	List<PizzeriaWeeklyDto> getPizzeriasByLocationId(long locationId);
	
	PizzeriaDto getPizzeriaDtoById(Long id);
	
	List<PizzeriaDto> getAllPizzerias();

    List<PizzeriaDto> getAllPizzeriasSortedByName();

    List<PizzeriaShowDto> getPizzeriasByPartOfName(String searchQuery);
	
	void add(PizzeriaDto dto);
	
	void edit(PizzeriaDto dto);
	
	Page<PizzeriaDto> getAllPizzeriasPage(Long location, Pageable pageable, String search);
}

