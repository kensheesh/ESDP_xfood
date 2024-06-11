package kg.attractor.xfood.service;

import kg.attractor.xfood.dto.checklist.CheckListResultDto;
import kg.attractor.xfood.dto.checklist.ChecklistExpertShowDto;

import java.util.List;

public interface CheckListService {
	
	List<ChecklistExpertShowDto> getUsersChecklists(String username, String status);

    CheckListResultDto getResult(Long checkListId);
}
