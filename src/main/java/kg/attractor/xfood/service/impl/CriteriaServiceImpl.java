package kg.attractor.xfood.service.impl;

import kg.attractor.xfood.model.Criteria;
import kg.attractor.xfood.repository.CriteriaRepository;
import kg.attractor.xfood.service.CriteriaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Slf4j
@Service
@RequiredArgsConstructor
public class CriteriaServiceImpl implements CriteriaService {
    private final CriteriaRepository criteriaRepository;

    @Override
    public Criteria getCriteriaById(Long criteriaId) {
        return criteriaRepository.findById(criteriaId)
                .orElseThrow(() -> new NoSuchElementException("Критерия с ID: " + criteriaId + " не найдена!"));
    }
}
