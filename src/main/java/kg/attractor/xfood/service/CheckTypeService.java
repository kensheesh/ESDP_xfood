package kg.attractor.xfood.service;

import kg.attractor.xfood.dto.checktype.CheckTypeSupervisorViewDto;
import kg.attractor.xfood.model.CheckType;

import java.util.List;

public interface CheckTypeService {
    List<CheckTypeSupervisorViewDto> getTypes();

    CheckType getById(Long checkTypeId);
}
