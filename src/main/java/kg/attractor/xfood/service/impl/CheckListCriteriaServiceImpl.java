package kg.attractor.xfood.service.impl;

import kg.attractor.xfood.model.CheckListsCriteria;
import kg.attractor.xfood.repository.ChecklistCriteriaRepository;
import kg.attractor.xfood.service.CheckListCriteriaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CheckListCriteriaServiceImpl implements CheckListCriteriaService {
    private final ChecklistCriteriaRepository criteriaRepository;

    @Override
    public void save(CheckListsCriteria checkListsCriteria) {
        criteriaRepository.save(checkListsCriteria);
        log.info("Saved checklist criteria: {}, {}, {}", checkListsCriteria.getCriteria(), checkListsCriteria.getMaxValue(), checkListsCriteria.getChecklist());
    }
}
