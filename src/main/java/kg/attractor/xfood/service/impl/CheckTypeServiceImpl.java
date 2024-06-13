package kg.attractor.xfood.service.impl;

import kg.attractor.xfood.dto.checktype.CheckTypeSupervisorViewDto;
import kg.attractor.xfood.model.CheckType;
import kg.attractor.xfood.repository.CheckTypeRepository;
import kg.attractor.xfood.service.CheckTypeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

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

    @Override
    public CheckType getById(Long checkTypeId) {
       return checkTypeRepository.findById(checkTypeId).orElseThrow(() -> new NoSuchElementException("No such checkTypeId: " + checkTypeId));
    }
}
