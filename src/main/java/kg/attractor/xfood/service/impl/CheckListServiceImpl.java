package kg.attractor.xfood.service.impl;

import kg.attractor.xfood.AuthParams;
import kg.attractor.xfood.dto.checklist.CheckListAnalyticsDto;
import kg.attractor.xfood.dto.checklist.CheckListResultDto;
import kg.attractor.xfood.dto.checklist.ChecklistMiniExpertShowDto;
import kg.attractor.xfood.dto.checklist.ChecklistShowDto;
import kg.attractor.xfood.enums.Role;
import kg.attractor.xfood.enums.Status;
import kg.attractor.xfood.exception.NotFoundException;
import kg.attractor.xfood.model.CheckList;
import kg.attractor.xfood.model.User;
import kg.attractor.xfood.repository.CheckListRepository;
import kg.attractor.xfood.repository.UserRepository;
import kg.attractor.xfood.service.CheckListService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class CheckListServiceImpl implements CheckListService {

    private final CheckListRepository checkListRepository;
    private final DtoBuilder dtoBuilder;
    private final UserRepository userRepository;

    @Override
    public ChecklistShowDto getCheckListById(Long id) {
        CheckList checkList = getModelCheckListById(id);
        return dtoBuilder.buildChecklistShowDto(checkList);
    }

    @Override
    public CheckList getModelCheckListById(Long id) {
        return checkListRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Чек-лист с ID:" + id + " не найден"));
    }

    @Override
    public void save(CheckList checkList) {
        checkListRepository.save(checkList);
    }

    @Override
    public List<ChecklistMiniExpertShowDto> getUsersChecklists(String username, Status status) {
        return checkListRepository.findCheckListByExpertEmailAndStatus(username, status)
                .stream()
                .map(dtoBuilder::buildChecklistDto)
                .toList();
    }

    @Override
    public CheckListResultDto getResult(Long checkListId) {
        return dtoBuilder.buildCheckListResultDto(
                checkListRepository.findByIdAndStatus(checkListId, Status.DONE)
                        .orElseThrow(() -> new NotFoundException("Check list not found"))
        );
    }

    @Override
    public List<CheckListAnalyticsDto> getAnalytics(String pizzeriaId, String managerId, String expertId, LocalDate startDate, LocalDate endDate) {
        User user = userRepository.findByEmail(AuthParams.getPrincipal().getUsername()).orElseThrow(() -> new NotFoundException("User not found"));
        List<CheckList> checkLists;
        if (user.getRole().equals(Role.EXPERT)) {
            checkLists = checkListRepository.findCheckListByExpertEmailAndStatus(user.getEmail(), Status.DONE);
        } else {
            checkLists = checkListRepository.findByStatus(Status.DONE);
        }

        if (!"default".equals(pizzeriaId)) {
            checkLists = checkLists.stream()
                    .filter(checkList -> checkList.getWorkSchedule().getPizzeria().getId().equals(Long.parseLong(pizzeriaId)))
                    .collect(Collectors.toList());

        }
        if (!"default".equals(managerId)) {
            checkLists = checkLists.stream()
                    .filter(checkList -> checkList.getWorkSchedule().getManager().getId().equals(Long.parseLong(managerId)))
                    .collect(Collectors.toList());
        }
        if (!"default".equals(expertId)) {
            checkLists = checkLists.stream()
                    .filter(checkList -> checkList.getOpportunity().getUser().getId().equals(Long.parseLong(expertId)))
                    .collect(Collectors.toList());
        }
        if (startDate != null && endDate != null) {
            checkLists = checkLists.stream()
                    .filter(checkList -> {
                        LocalDate date = checkList.getWorkSchedule().getDate().toLocalDate();
                        return (date.isEqual(startDate) || date.isAfter(startDate)) &&
                                (date.isEqual(endDate) || date.isBefore(endDate));
                    })
                    .collect(Collectors.toList());
        }

        return checkLists.stream()
                .map(dtoBuilder::buildCheckListAnalyticsDto)
                .collect(Collectors.toList());
    }

    @Override
    public CheckListResultDto getResultByUuidLink(String uuidLink) {
        return dtoBuilder.buildCheckListResultDto(
                checkListRepository.findByUuidLinkAndStatus(uuidLink, Status.DONE)
                        .orElseThrow(() -> new NotFoundException("Check list not found"))
        );
    }
}