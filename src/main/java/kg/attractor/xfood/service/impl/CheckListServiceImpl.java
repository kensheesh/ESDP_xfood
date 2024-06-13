package kg.attractor.xfood.service.impl;

import kg.attractor.xfood.dto.checklist.CheckListResultDto;
import kg.attractor.xfood.dto.checklist.ChecklistMiniExpertShowDto;
import kg.attractor.xfood.enums.Status;
import kg.attractor.xfood.exception.NotFoundException;
import kg.attractor.xfood.dto.checklist.ChecklistShowDto;
import kg.attractor.xfood.model.CheckList;
import kg.attractor.xfood.repository.CheckListRepository;
import kg.attractor.xfood.service.CheckListService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
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
        return dtoBuilder.buildChecklistShowDto(checkList);
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

	@Override
	public List<ChecklistMiniExpertShowDto> getUsersChecklists(String username, Status status) {
		return checkListRepository.findCheckListByExpertEmailAndStatus(username, status)
				.stream()
				.map(dtoBuilder :: buildChecklistDto)
				.toList();
	}

	@Override
	public CheckListResultDto getResult(Long checkListId) {
		return dtoBuilder.buildCheckListResultDto(
				checkListRepository.findByIdAndStatus(checkListId, Status.DONE)
						.orElseThrow(() -> new NotFoundException("Check list not found"))
		);
	}

}
