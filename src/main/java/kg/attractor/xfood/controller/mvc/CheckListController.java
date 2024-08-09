package kg.attractor.xfood.controller.mvc;

import kg.attractor.xfood.AuthParams;
import kg.attractor.xfood.dto.checklist.CheckListMiniSupervisorCreateDto;
import kg.attractor.xfood.dto.checklist.CheckListSupervisorCreateDto;
import kg.attractor.xfood.dto.checklist.CheckListSupervisorEditDto;
import kg.attractor.xfood.dto.checklist.ChecklistShowDto;
import kg.attractor.xfood.dto.comment.CommentDto;
import kg.attractor.xfood.dto.criteria.CriteriaSupervisorCreateDto;
import kg.attractor.xfood.dto.user.UserDto;
import kg.attractor.xfood.enums.Role;
import kg.attractor.xfood.enums.Status;
import kg.attractor.xfood.service.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Collection;

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
    private final SettingService settingService;


    //    // ROLE: SUPERVISOR
    @GetMapping("/create")
    public String create(@RequestParam(name = "date") LocalDate date, @RequestParam(name = "managerId") Long managerId, @RequestParam(name = "expertId") Long expertId, Model model) {
        model.addAttribute("zones", zoneService.getZones());
        model.addAttribute("sections", sectionService.getSections());
        model.addAttribute("workSchedule", workScheduleService.getWorkSchedule(managerId, date));
        model.addAttribute("types", checkTypeService.getTypes());
        model.addAttribute("criteriaSupervisorCreateDto", new CriteriaSupervisorCreateDto());
        model.addAttribute("date", date);
        model.addAttribute("managerId", managerId);
        model.addAttribute("expertId", expertId);
        return "checklist/create";
    }


    // ROLE: SUPERVISOR
    @PostMapping("/create")
    public String create(CheckListSupervisorCreateDto createDto) {
        CheckListMiniSupervisorCreateDto checklistDto = checkListService.create(createDto);
        checkListService.bindChecklistWithCriterion(checklistDto);
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

    @GetMapping("{uuid}")
    public String getCheck(@PathVariable String uuid, Model model, Authentication auth) {
        if(auth == null) {
            model.addAttribute("guest", true);
        }
        ChecklistShowDto checkList = checkListService.getCheckListById(uuid);
        model.addAttribute("checkList", checkList);
        boolean isRecent = settingService.isCheckRecent(checkList);
        model.addAttribute("isRecent", settingService.isCheckRecent(checkList));
        return "checklist/result";
    }

    @GetMapping("{uuid}/fill")
    public String getCheckForFill(@PathVariable String uuid, Model model) {
        Collection<? extends GrantedAuthority> authorities = AuthParams.getAuth().getAuthorities();
        ChecklistShowDto checkListDto = checkListService.getCheckListById(uuid);
        String role = authorities.stream().toList().get(0).getAuthority();
        if (role.equalsIgnoreCase("role_expert")) {
            String authExpertEmail = AuthParams.getPrincipal().getUsername();
            if (authExpertEmail.equals(checkListDto.getExpertEmail())) {
                model.addAttribute("checkList", checkListDto);
            } else {
                model.addAttribute("error", "Эта проверка не назначена на вас!");
            }
        } else {
            model.addAttribute("checkList", checkListDto);
        }
        return "checklist/check_list";
    }

    // ROLE: EXPERT
    @PostMapping("/{id}/check")
    public String check(@PathVariable(name = "id") Long checkListId, BindingResult result, Model model) {
        /* TODO:
            Подтверждение проверки экспертом
        */

        return null;
    }

    // ROLE: SUPERVISOR, ADMIN
    @GetMapping("/{id}/change")
    public String changeResult(@PathVariable(name = "id") Long checkListId, Model model) {
        /* TODO:
            Изменение результата проверки *после
            подтверждения экспертом
        */

        return null;
    }

    // ROLE: SUPERVISOR, ADMIN
    @PostMapping("/{id}/change")
    public String changeResult(@PathVariable(name = "id") Long checkListId, BindingResult result, Model model) {
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
            model.addAttribute("guest", true);
        }
        if(checkList.getStatus().equals(Status.DONE)) {
            model.addAttribute("checkList", checkList);
            model.addAttribute("isRecent", settingService.isCheckRecent(checkList));
        } else {
            model.addAttribute("error",
                    "Данный чеклист еще не опубликован или такого чеклиста не существует!");
        }

        return "checklist/result";
    }

    // ROLE: SUPERVISOR
    @GetMapping("/{id}/update")
    public String edit(@PathVariable(name = "id") String uuid, Model model) {
        model.addAttribute("zones", zoneService.getZones());
        model.addAttribute("sections", sectionService.getSections());
        model.addAttribute("checklist", checkListService.getChecklistByUuid(uuid));
        model.addAttribute("experts", userService.getAllExperts());
        model.addAttribute("managers", managerService.getAllAvailable(uuid));
        return "checklist/edit";
    }

    // ROLE: SUPERVISOR
    @PostMapping("/{id}/update")
    public String edit(@PathVariable(name = "id") String uuid, CheckListSupervisorEditDto checkList) {
        checkListService.edit(checkList);
        return "redirect:/checks/" + uuid + "/check";
    }

    @PreAuthorize("hasAnyRole('SUPERVISOR','ADMIN')")
    @PostMapping("{uuid}/delete")
    public String delete(@PathVariable String uuid) {
        checkListService.delete(uuid);
        return "redirect:/expert/checks";
    }

    @PostMapping("/{uuid}/{criteriaId}")
    public String comment(@PathVariable(name = "uuid") String uuid, @PathVariable(name = "criteriaId") Long
            criteriaId, CommentDto commentDto, Model model) {
        checkListService.comment(uuid, criteriaId, commentDto);
        return "redirect:/checks/" + uuid + "/check";

    }

    @PostMapping("{uuid}/restore")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public String restore(@PathVariable String uuid) {
        checkListService.restore(uuid);
        return "redirect:/expert/checks";
    }

}
