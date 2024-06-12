package kg.attractor.xfood.service.impl;

import kg.attractor.xfood.dto.pizzeria.PizzeriaDto;
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
    private final DtoBuilder dtoBuilder;
    private final PizzeriaRepository pizzeriaRepository;

    @Override
    public List<PizzeriaDto> getAllPizzerias() {
        return pizzeriaRepository.findAll()
                .stream()
                .map(dtoBuilder::buildPizzeriaDto)
                .toList();
    }
}
