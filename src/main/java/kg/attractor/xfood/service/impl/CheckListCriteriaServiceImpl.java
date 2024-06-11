package kg.attractor.xfood.service.impl;

import kg.attractor.xfood.dto.criteria.SaveCriteriaDto;
import kg.attractor.xfood.enums.Status;
import kg.attractor.xfood.model.CheckList;
import kg.attractor.xfood.model.CheckListsCriteria;
import kg.attractor.xfood.repository.CheckListCriteriaRepository;
import kg.attractor.xfood.service.CheckListCriteriaService;
import kg.attractor.xfood.service.CheckListService;
import kg.attractor.xfood.service.CriteriaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CheckListCriteriaServiceImpl implements CheckListCriteriaService {
    private final CheckListCriteriaRepository checkListCriteriaRepository;
    private final CriteriaService criteriaService;
    private final CheckListService checkListService;

    @Override
    public void save(SaveCriteriaDto saveCriteriaDto) {
        CheckList checkList = checkListService.getModelCheckListById(saveCriteriaDto.getCheckListId());
        if(!checkList.getStatus().equals(Status.DONE)) {
            CheckListsCriteria checkListsCriteria = new CheckListsCriteria();

            checkListsCriteria.setCriteria(criteriaService.getCriteriaById(saveCriteriaDto.getCriteriaId()));
            checkListsCriteria.setChecklist(checkListService.getModelCheckListById(saveCriteriaDto.getCheckListId()));
            checkListsCriteria.setValue(saveCriteriaDto.getValue());
            checkListsCriteria.setMaxValue(saveCriteriaDto.getMaxValue());

            checkListCriteriaRepository.save(checkListsCriteria);
            checkList.setStatus(Status.IN_PROGRESS);
            checkListService.save(checkList);
        }

    }
}
