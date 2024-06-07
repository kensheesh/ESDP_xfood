package kg.attractor.xfood.service.impl;

import kg.attractor.xfood.dto.checklist.ChecklistDto;
import kg.attractor.xfood.model.CheckList;
import kg.attractor.xfood.repository.CheckListRepository;
import kg.attractor.xfood.service.CheckListService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

import static kg.attractor.xfood.enums.Status.DONE;
import static kg.attractor.xfood.enums.Status.IN_PROGRESS;
import static kg.attractor.xfood.enums.Status.NEW;

@Slf4j
@Service
@RequiredArgsConstructor
public class CheckListServiceImpl implements CheckListService {
	
	private final CheckListRepository checkListRepository;
	
	private final CriteriaServiceImpl criteriaService;
	
	public List<ChecklistDto> getUsersChecklists(String username, String status) {
		List<CheckList> checkLists;
		
		if (status.equalsIgnoreCase(NEW.getStatus())) {
			checkLists = checkListRepository.findByOpportunityUserEmailAndStatus(username, NEW);
		} else if (status.equalsIgnoreCase(IN_PROGRESS.getStatus())) {
			checkLists = checkListRepository.findByOpportunityUserEmailAndStatus(username, IN_PROGRESS);
		} else if (status.equalsIgnoreCase(DONE.getStatus())) {
			checkLists = checkListRepository.findByOpportunityUserEmailAndStatus(username, DONE);
		} else {
			throw new NoSuchElementException("No such status " + status);
		}
		
		return checkLists.stream().map(this :: buildChecklistDto).toList();
	}
	
	protected ChecklistDto buildChecklistDto(CheckList model) {
//		List<CheckListsCriteria> temp = model.getCheckListsCriteria();
//		temp.stream().map()
//
//		List<CriteriaDto> criteria = model.getCriteria()
//				.stream()
//				.map(criteriaService :: buildCriteriaDto)
//				.toList();
		
		return ChecklistDto.builder()
				.id(model.getId())
				.status(model.getStatus())

//				.criteria(criteria)
				.build();
	}
}
