package kg.attractor.xfood.service;

import kg.attractor.xfood.dto.manager.ManagerDto;
import kg.attractor.xfood.dto.okhttp.PizzeriaStaffMemberDto;
import kg.attractor.xfood.model.Manager;

import java.util.List;

public interface ManagerService {
    List<ManagerDto> getAllManagers();
    List<ManagerDto> getAllManagersAscBySurname();

    List<PizzeriaStaffMemberDto> getAllAvailable(String uuid);

    Manager findByPhoneNumber(String uuid);
    Manager findById(Long uuid);
    ManagerDto getManagerDtoById(Long id);
}
