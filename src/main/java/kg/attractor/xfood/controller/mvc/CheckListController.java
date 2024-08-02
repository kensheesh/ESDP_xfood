package kg.attractor.xfood.controller.mvc;

import kg.attractor.xfood.AuthParams;
import kg.attractor.xfood.dto.checklist.CheckListMiniSupervisorCreateDto;
import kg.attractor.xfood.dto.checklist.CheckListSupervisorCreateDto;
import kg.attractor.xfood.dto.checklist.CheckListSupervisorEditDto;
import kg.attractor.xfood.dto.checklist.ChecklistShowDto;
import kg.attractor.xfood.dto.criteria.CriteriaSupervisorCreateDto;
import kg.attractor.xfood.enums.Status;
import kg.attractor.xfood.service.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/checks")
public class CheckListController {
    private final CheckListService checkListService;
    private final CheckTypeService checkTypeService;
    private final CriteriaService criteriaService;
    private final ZoneService zoneService;
    private final SectionService sectionService;
    private final WorkScheduleService workScheduleService;
    private final UserService userService;
    private final ManagerService managerService;
    private final OpportunityService opportunityService;


//    // ROLE: SUPERVISOR
@GetMapping("/create")
public String create (@RequestParam(name = "date") LocalDate date,  @RequestParam(name ="managerId") Long managerId, @RequestParam(name = "expertId")Long expertId, Model model) {
    model.addAttribute("zones",zoneService.getZones() );
    model.addAttribute("sections", sectionService.getSections());
    model.addAttribute("workSchedule", workScheduleService.getWorkSchedule(managerId,date));
    model.addAttribute("types",checkTypeService.getTypes());
    model.addAttribute("criteriaSupervisorCreateDto", new CriteriaSupervisorCreateDto());
    model.addAttribute("date", date);
    model.addAttribute("managerId", managerId);
    model.addAttribute("expertId", expertId);
    return "checklist/create";
}


    // ROLE: SUPERVISOR
    @PostMapping("/create")
    public String create (CheckListSupervisorCreateDto createDto) {
        CheckListMiniSupervisorCreateDto checklistDto =  checkListService.create(createDto);
        checkListService.bindChecklistWithCriterion(checklistDto );
        return "redirect:/supervisor/weekly";
    }

//    // ROLE: ADMIN
//    @GetMapping ("/{id}/edit")
//    public String edit (@PathVariable (name="id") Long checkListId, Model model) {
//        return null;
//    }
//
//    // ROLE: ADMIN
//    @PostMapping ("/{id}/edit")
//    public String edit (@PathVariable (name="id") Long checkListId, BindingResult result, Model model) {
//        return null;
//    }

    // ROLE: EXPERT
    @GetMapping ("/{id}/check")
    public String check (@PathVariable (name="id") String checkListId, Model model) {
        ChecklistShowDto checkListDto = checkListService.getCheckListById(checkListId);
        model.addAttribute("checkList", checkListDto);
        return "checklist/check_list";
    }

    // ROLE: EXPERT
    @PostMapping ("/{id}/check")
    public String check (@PathVariable (name="id") Long checkListId, BindingResult result, Model model) {
        /* TODO:
            Подтверждение проверки экспертом
        */

        return null;
    }

    // ROLE: SUPERVISOR, ADMIN
    @GetMapping ("/{id}/change")
    public String changeResult (@PathVariable (name="id") Long checkListId, Model model) {
        /* TODO:
            Изменение результата проверки *после
            подтверждения экспертом
        */

        return null;
    }

    // ROLE: SUPERVISOR, ADMIN
    @PostMapping ("/{id}/change")
    public String changeResult (@PathVariable (name="id") Long checkListId, BindingResult result, Model model) {
        /* TODO:
            Изменение результата проверки *после
            подтверждения экспертом
        */

        return null;
    }

    @GetMapping ("/{id}/result")
    public String getResult (@PathVariable (name = "id") String checkListId, Model model, Authentication auth) {
        ChecklistShowDto checkList = checkListService.getCheckListById(checkListId);
        if(auth == null) {
            model.addAttribute("guess", true);
        }
        if(checkList.getStatus().equals(Status.DONE)) {
            model.addAttribute("checkList", checkList);
        } else {
            model.addAttribute("error",
                    "Данный чеклист еще не опубликован или такого чеклиста не существует!");
        }

        return "checklist/result";
    }

    // ROLE: SUPERVISOR
    @GetMapping ("/{id}/update")
    public String edit (@PathVariable (name="id") String uuid, Model model) {
            model.addAttribute("zones",zoneService.getZones() );
            model.addAttribute("sections", sectionService.getSections());
            model.addAttribute("checklist", checkListService.getChecklistByUuid(uuid));
            model.addAttribute("experts", userService.getAllExperts());
            model.addAttribute("managers", managerService.getAllAvailable(uuid));
        return "checklist/edit";
    }

    // ROLE: SUPERVISOR
    @PostMapping ("/{id}/update")
    public String edit (@PathVariable (name="id") String uuid, CheckListSupervisorEditDto checkList) {
        checkListService.edit(checkList);
        return "redirect:/checks/"+uuid+"/check";
    }

    @PostMapping("{uuid}/delete")
    public String delete (@PathVariable String uuid) {
        checkListService.delete(uuid);
        return "redirect:/expert/checks";
    }

    @PostMapping("{uuid}/restore")
    public String restore (@PathVariable String uuid) {
        checkListService.restore(uuid);
        return "redirect:/checks";
    }

}
