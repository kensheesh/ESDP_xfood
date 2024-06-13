package kg.attractor.xfood.service.impl;

import kg.attractor.xfood.dto.manager.ManagerDto;
import kg.attractor.xfood.repository.ManagerRepository;
import kg.attractor.xfood.service.ManagerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ManagerServiceImpl implements ManagerService {
    private final DtoBuilder dtoBuilder;
    private final ManagerRepository managerRepository;

    @Override
    public List<ManagerDto> getAllManagers() {
        return managerRepository.findAll()
                .stream()
                .map(dtoBuilder::buildManagerDto)
                .toList();
    }
}
