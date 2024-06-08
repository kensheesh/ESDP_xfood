package kg.attractor.xfood.service.impl;

import kg.attractor.xfood.dto.checklist.ChecklistExpertShowDto;
import kg.attractor.xfood.enums.Status;
import kg.attractor.xfood.repository.CheckListRepository;
import kg.attractor.xfood.service.CheckListService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CheckListServiceImpl implements CheckListService {
	
	private final CheckListRepository checkListRepository;
	private final DtoBuilder dtoBuilder;
	
	@Override
	public List<ChecklistExpertShowDto> getUsersChecklists(String username, String status) {
		return checkListRepository.findCheckListByExpertEmailAndStatus(username, Status.getStatusEnum(status))
				.stream()
				.map(dtoBuilder :: buildChecklistDto)
				.toList();
	}
	
}
