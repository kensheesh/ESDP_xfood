package kg.attractor.xfood.service.impl;

import kg.attractor.xfood.dto.pizzeria.PizzeriaShowDto;
import kg.attractor.xfood.model.Pizzeria;
import kg.attractor.xfood.repository.PizzeriaRepository;
import kg.attractor.xfood.service.PizzeriaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class PizzeriaServiceImpl implements PizzeriaService {
    private final PizzeriaRepository pizzeriaRepository;
    private final DtoBuilder dtoBuilder;

    public List<PizzeriaShowDto> getPizzeriaByLocationId(long locationId) {
        List<Pizzeria> pizzerias = pizzeriaRepository.findByLocation_IdOrderByNameAsc(locationId);
        return dtoBuilder.buildPizzeriaShowDtos(pizzerias);
    }
}
