package kg.attractor.xfood.service;

import kg.attractor.xfood.dto.pizzeria.PizzeriaDto;
import kg.attractor.xfood.dto.pizzeria.PizzeriaShowDto;
import kg.attractor.xfood.model.Pizzeria;

import java.util.List;

public interface PizzeriaService {
    List<PizzeriaShowDto> getPizzeriasByLocationId(long locationId);
    List<PizzeriaDto> getAllPizzerias();
}
