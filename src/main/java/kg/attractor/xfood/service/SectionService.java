package kg.attractor.xfood.service;

import kg.attractor.xfood.dto.SectionSupervisorShowDto;
import kg.attractor.xfood.dto.settings.TemplateCreateDto;
import kg.attractor.xfood.model.Section;

import java.util.List;

public interface SectionService {
    List<SectionSupervisorShowDto> getSections();
    List<SectionSupervisorShowDto> getSectionsWithoutCritAndWow();
    Section findByName(String section);
}
