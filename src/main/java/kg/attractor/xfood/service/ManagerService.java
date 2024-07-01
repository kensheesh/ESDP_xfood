package kg.attractor.xfood.service;

import kg.attractor.xfood.dto.manager.ManagerDto;
import kg.attractor.xfood.model.Manager;

import java.util.List;

public interface ManagerService {
    List<ManagerDto> getAllManagers();

    List<ManagerDto> getAllAvailable(String uuid);

    Manager findById(Long id);
    ManagerDto getManagerDtoById(Long id);
}
