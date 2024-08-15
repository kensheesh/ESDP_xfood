package kg.attractor.xfood.mockito.service;

import kg.attractor.xfood.dto.SectionSupervisorShowDto;
import kg.attractor.xfood.model.Section;
import kg.attractor.xfood.repository.SectionRepository;
import kg.attractor.xfood.service.impl.DtoBuilder;
import kg.attractor.xfood.service.impl.SectionServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


class SectionServiceTest {
    @InjectMocks
    private SectionServiceImpl sectionService;

    @Mock
    private SectionRepository sectionRepository;

    @Mock
    private DtoBuilder dtoBuilder;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllSections() {
        Section section = new Section();
        List<Section> sections = List.of(section);
        SectionSupervisorShowDto dto = new SectionSupervisorShowDto();
        List<SectionSupervisorShowDto> dtoList = List.of(dto);

        when(sectionRepository.findAll()).thenReturn(sections);
        when(dtoBuilder.buildSectionDto(any(Section.class))).thenReturn(dto);

        List<SectionSupervisorShowDto> found = sectionService.getSections();

        assertNotNull(found);
        assertEquals(dtoList.size(), found.size());
    }

    @Test
    void testFindByName(){
        String name = "name";
        Section section = Section.builder().name(name).build();
        when(sectionRepository.findByName(name)).thenReturn(Optional.of(section));

        Section found =  sectionService.findByName(name);

        assertNotNull(found);
        assertEquals(name, found.getName());
    }

    @Test
    void testFindByName_ThrowsNoSuchElementException() {
        String name = "name";
        NoSuchElementException exception = Assertions.assertThrows(NoSuchElementException.class, () -> sectionService.findByName(name));
        assertEquals("Раздел с именем " + name + " не найден", exception.getMessage());
    }
}
