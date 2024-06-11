package kg.attractor.xfood.service;

import kg.attractor.xfood.dto.checklist.ChecklistMiniExpertShowDto;
import kg.attractor.xfood.enums.Status;

import java.util.List;

public interface CheckListService {
	
	List<ChecklistMiniExpertShowDto> getUsersChecklists(String username, Status status);
	
}
