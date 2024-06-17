package kg.attractor.xfood.service.impl;

import kg.attractor.xfood.model.CriteriaPizzeria;
import kg.attractor.xfood.repository.CriteriaPizzeriaRepository;
import kg.attractor.xfood.service.CriteriaPizzeriaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CriteriaPizzeriaServiceImpl implements CriteriaPizzeriaService {
    private final CriteriaPizzeriaRepository criteriaPizzeriaRepository;

    @Override
    public void save(CriteriaPizzeria criteriaPizzeria) {
        criteriaPizzeriaRepository.save(criteriaPizzeria);
        log.info("CriteriaPizzeria saved {}", criteriaPizzeria);
    }
}
