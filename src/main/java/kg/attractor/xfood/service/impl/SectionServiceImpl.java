package kg.attractor.xfood.service.impl;

import kg.attractor.xfood.dto.SectionSupervisorShowDto;
import kg.attractor.xfood.model.Section;
import kg.attractor.xfood.repository.SectionRepository;
import kg.attractor.xfood.service.SectionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class SectionServiceImpl implements SectionService {
    private final SectionRepository sectionRepository;
    private final DtoBuilder dtoBuilder;

    @Override
    public List<SectionSupervisorShowDto> getSections() {
        return sectionRepository.findAll().stream().map(dtoBuilder::buildSectionDto).toList();
    }

    @Override
    public Section findByName(String section) {
        return sectionRepository.findByName(section);
    }
}
