package kg.attractor.xfood.service;

import kg.attractor.xfood.dto.pizzeria.PizzeriaDto;
import kg.attractor.xfood.dto.pizzeria.PizzeriaShowDto;
import kg.attractor.xfood.dto.pizzeria.PizzeriaWeeklyDto;
import kg.attractor.xfood.model.Pizzeria;

import java.util.List;

public interface PizzeriaService {
    List<PizzeriaWeeklyDto> getPizzeriasByLocationId(long locationId);
    PizzeriaDto getPizzeriaDtoById(long id);
    List<PizzeriaDto> getAllPizzerias();

    List<PizzeriaShowDto> getPizzeriasByPartOfName(String searchQuery);
}
