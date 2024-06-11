package kg.attractor.xfood.service.impl;

import kg.attractor.xfood.dto.checklist.ChecklistShowDto;
import kg.attractor.xfood.model.CheckList;
import kg.attractor.xfood.repository.CheckListRepository;
import kg.attractor.xfood.service.CheckListService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Slf4j
@Service
@RequiredArgsConstructor
public class CheckListServiceImpl implements CheckListService {

    private final CheckListRepository checkListRepository;
    private final DtoBuilder dtoBuilder;

    @Override
    public ChecklistShowDto getCheckListById(Long id) {
        CheckList checkList = getModelCheckListById(id);
        return dtoBuilder.buildChecklistDto(checkList);
    }

    @Override
    public CheckList getModelCheckListById(Long id) {
        return checkListRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Чек-лист с ID:" + id + " не найден"));
    }

    @Override
    public void save(CheckList checkList) {
        checkListRepository.save(checkList);
    }

}
