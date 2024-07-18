package kg.attractor.xfood.service.impl;

import kg.attractor.xfood.dto.manager.ManagerDto;
import kg.attractor.xfood.dto.okhttp.PizzeriaStaffMemberDto;
import kg.attractor.xfood.exception.NotFoundException;
import kg.attractor.xfood.model.CheckList;
import kg.attractor.xfood.model.Manager;
import kg.attractor.xfood.model.Manager;
import kg.attractor.xfood.model.Pizzeria;
import kg.attractor.xfood.repository.CheckListRepository;
import kg.attractor.xfood.repository.ManagerRepository;
import kg.attractor.xfood.service.CheckListService;
import kg.attractor.xfood.service.ManagerService;
import kg.attractor.xfood.service.OkHttpService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Slf4j
@Service
@RequiredArgsConstructor
public class ManagerServiceImpl implements ManagerService {
    private final DtoBuilder dtoBuilder;
    private final ManagerRepository managerRepository;
    private final CheckListService checkListService;
    private  OkHttpService okHttpService;

    @Autowired
    public void setOkHttpServiceService(@Lazy OkHttpService okHttpService) {
        this.okHttpService = okHttpService;
    }

    @Override
    public List<ManagerDto> getAllManagers() {
        return managerRepository.findAll()
                .stream()
                .map(dtoBuilder::buildManagerDto)
                .toList();
    }

    @Override
    public List<PizzeriaStaffMemberDto> getAllAvailable(String uuid) {
        CheckList checkList = checkListService.getModelCheckListById(uuid);
        Pizzeria pizzeria = checkList.getWorkSchedule().getPizzeria();
        return  okHttpService.getPizzeriaStaff(pizzeria.getLocation().getCountryCode(), pizzeria.getUuid());
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
