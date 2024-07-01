package kg.attractor.xfood.service.impl;

import kg.attractor.xfood.dto.pizzeria.PizzeriaDto;
import kg.attractor.xfood.dto.pizzeria.PizzeriaWeeklyDto;
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

    @Override
    public List<PizzeriaWeeklyDto> getPizzeriasByLocationId(long locationId) {
        List<Pizzeria> pizzerias = pizzeriaRepository.findByLocation_IdOrderByNameAsc(locationId);
        log.info("Размер списка пиццерий: " + pizzerias.size());
        return dtoBuilder.buildPizzeriaWeeklyDtos(pizzerias);
    }

    @Override
    public List<PizzeriaDto> getAllPizzerias() {
        return pizzeriaRepository.findAll()
                .stream()
                .map(dtoBuilder::buildPizzeriaDto)
                .toList();
    }

    @Override
    public PizzeriaDto getPizzeriaDtoById(long id) {
        Pizzeria pizzeria = pizzeriaRepository.findById(id).orElseThrow(null);
        return dtoBuilder.buildPizzeriaDto(pizzeria);
    }


    protected Pizzeria getPizzeriaById(Long id) {
        return pizzeriaRepository.findById(id).orElse(null);
    }

    protected Pizzeria getPizzeriaByUuid(String unitId) {
        return pizzeriaRepository.findByUuidEqualsIgnoreCase(unitId);
    }
}
