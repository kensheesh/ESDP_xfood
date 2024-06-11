package kg.attractor.xfood.service;

import kg.attractor.xfood.dto.checklist.ChecklistShowDto;

public interface CheckListService {

    ChecklistShowDto getCheckListById(Long id);
}
