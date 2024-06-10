package kg.attractor.xfood.service.impl;

import kg.attractor.xfood.dto.checktype.CheckTypeSupervisorViewDto;
import kg.attractor.xfood.repository.CheckTypeRepository;
import kg.attractor.xfood.service.CheckTypeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CheckTypeServiceImpl implements CheckTypeService {
    private final CheckTypeRepository checkTypeRepository;
    private final DtoBuilder dtoBuilder;
    @Override
    public List<CheckTypeSupervisorViewDto> getTypes() {
        return checkTypeRepository.findAll().stream().map(dtoBuilder::buildCheckTypeShowDto).toList();
    }
}
