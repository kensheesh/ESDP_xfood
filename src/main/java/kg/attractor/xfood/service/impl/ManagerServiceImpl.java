package kg.attractor.xfood.service.impl;

import kg.attractor.xfood.dto.manager.ManagerDto;
import kg.attractor.xfood.exception.NotFoundException;
import kg.attractor.xfood.model.Manager;
import kg.attractor.xfood.model.Manager;
import kg.attractor.xfood.repository.ManagerRepository;
import kg.attractor.xfood.service.ManagerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

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

    @Override
    public List<ManagerDto> getAllAvailable(String uuid) {
        //TODO после исправления бд доставать только менеджеров данной пиццерии
        //TODO написать тест после исправления
        return managerRepository.findAll()
                .stream()
                .map(dtoBuilder::buildManagerDto)
                .toList();
    }

    @Override
    public Manager findById(Long id) {
      return managerRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Manager not found by id: " + id));
    }

    @Override
    public ManagerDto getManagerDtoById(Long id) {
        Manager manager =  findById(id);
        return dtoBuilder.buildManagerDto(manager);
    }


    protected Manager getManagersByUuid(String staffId) {
		return managerRepository.findByUuid(staffId);
	}
	
	public void addManager(Manager manager) {
		managerRepository.saveAndFlush(manager);
	}
}
