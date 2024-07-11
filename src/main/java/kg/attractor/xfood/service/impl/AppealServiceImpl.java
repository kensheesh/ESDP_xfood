package kg.attractor.xfood.service.impl;

import kg.attractor.xfood.dto.appeal.AppealDto;
import kg.attractor.xfood.dto.appeal.DataAppealDto;
import kg.attractor.xfood.model.Appeal;
import kg.attractor.xfood.model.CheckListsCriteria;
import kg.attractor.xfood.repository.AppealRepository;
import kg.attractor.xfood.service.AppealService;
import kg.attractor.xfood.service.CheckListCriteriaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class AppealServiceImpl implements AppealService {

    private final CheckListCriteriaService checkListCriteriaService;
    private final AppealRepository appealRepository;
    private final DtoBuilder dtoBuilder;

    @Override
    public Long create(DataAppealDto dto) {
        CheckListsCriteria checkListsCriteria = checkListCriteriaService
                .findByCriteriaIdAndChecklistId(dto.getCriteriaId(), dto.getCheckListId());

        Appeal appeal = Appeal.builder()
                .checkListsCriteria(checkListsCriteria)
                .comment("")
                .email("")
                .fullName("")
                .build();

        return appealRepository.save(appeal).getId();
    }

    @Override
    public AppealDto findById(Long id) {
        Appeal appeal = appealRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Аппеляция не найдена"));
        return dtoBuilder.buildAppealDto(appeal);
    }
}
