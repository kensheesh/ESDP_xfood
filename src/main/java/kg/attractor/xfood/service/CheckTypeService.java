package kg.attractor.xfood.service;

import kg.attractor.xfood.dto.checktype.CheckTypeSupervisorViewDto;

import java.util.List;

public interface CheckTypeService {
    List<CheckTypeSupervisorViewDto> getTypes();
}
