package kg.attractor.xfood.service.impl;

import kg.attractor.xfood.dto.pizzeria.PizzeriaDto;
import kg.attractor.xfood.dto.pizzeria.PizzeriaShowDto;
import kg.attractor.xfood.dto.pizzeria.PizzeriaWeeklyDto;
import kg.attractor.xfood.exception.NotFoundException;
import kg.attractor.xfood.model.Pizzeria;
import kg.attractor.xfood.repository.PizzeriaRepository;
import kg.attractor.xfood.service.PizzeriaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class PizzeriaServiceImpl implements PizzeriaService {

    private final PizzeriaRepository pizzeriaRepository;
    private final LocationServiceImpl locationService;
    private final DtoBuilder dtoBuilder;
    private final ModelBuilder modelBuilder;

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
    public List<PizzeriaShowDto> getPizzeriasByPartOfName(String query) {
        if (query == null || query.isEmpty()) {
            List<Pizzeria> list = pizzeriaRepository.findAll();
            return dtoBuilder.buildPizzeriaShowDtos(list);
        }
        return pizzeriaRepository.findByNameContainingIgnoreCase(query);
    }
    
    @Override
    public void add(PizzeriaDto dto) {
        Pizzeria p = modelBuilder.buildPizzeria(dto, locationService.findLocationById(dto.getLocationId()));
        pizzeriaRepository.save(p);
    }
    
    @Modifying
    @Override
    public void edit(PizzeriaDto dto) {
        Pizzeria p = modelBuilder.buildPizzeria(dto, locationService.findLocationById(dto.getLocation().getId()));
        pizzeriaRepository.save(p);
    }
    
    @Override
    public PizzeriaDto getPizzeriaDtoById(Long id) {
        Pizzeria pizzeria = pizzeriaRepository.findById(id).orElseThrow(() -> new NotFoundException("Pizzeria not found"));
        return dtoBuilder.buildPizzeriaDto(pizzeria);
    }
    
    protected Pizzeria getPizzeriaById(Long id) {
        return pizzeriaRepository.findById(id).orElseThrow(() -> new NotFoundException("Pizzeria not found"));
    }

    protected Pizzeria getPizzeriaByUuid(String unitId) {
        return pizzeriaRepository.findByUuidEqualsIgnoreCase(unitId);
    }
}
