package kg.attractor.xfood.mockito.service;

import kg.attractor.xfood.dto.criteria.CriteriaSupervisorCreateDto;
import kg.attractor.xfood.dto.criteria.CriteriaSupervisorShowDto;
import kg.attractor.xfood.model.Criteria;
import kg.attractor.xfood.model.Section;
import kg.attractor.xfood.model.Zone;
import kg.attractor.xfood.repository.CriteriaRepository;
import kg.attractor.xfood.service.SectionService;
import kg.attractor.xfood.service.ZoneService;
import kg.attractor.xfood.service.impl.CriteriaServiceImpl;
import kg.attractor.xfood.service.impl.DtoBuilder;
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


class CriteriaServiceTest {
    @InjectMocks
    private CriteriaServiceImpl criteriaService;

    @Mock
    private CriteriaRepository criteriaRepository;
    @Mock
    private SectionService sectionService;
    @Mock
    private ZoneService zoneService;
    @Mock
    private Criteria criteriaTest;
    @Mock
    private DtoBuilder dtoBuilder;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        criteriaTest = Criteria.builder()
                .id(1L)
                .description("Description for Test Criteria")
                .build();
    }

    @Test
    void testFIndById() {
        Criteria criteria = Criteria.builder().id(1L).coefficient(1).description("Description").zone(Zone.builder().build()).section(Section.builder().build()).build();
        when(criteriaRepository.findById(1L)).thenReturn(Optional.of(criteria));
        Criteria result = criteriaService.findById(1L);
        assertNotNull(result);
        assertEquals(result, criteria);
    }

    @Test
    void testGetByDescription() {
        List<Criteria> criterionByDescription = List.of(Criteria.builder().description("Description").zone(Zone.builder().build()).build());
        when(criteriaRepository.findCriterionByDescriptionContainingIgnoreCase("Description")).thenReturn(criterionByDescription);

        List<Criteria> foundByDescription = criteriaRepository.findCriterionByDescriptionContainingIgnoreCase("Description");

        assertNotNull(foundByDescription);
        assertEquals(foundByDescription, criterionByDescription);
        assertEquals(foundByDescription.size(), criterionByDescription.size());
    }

    @Test
    void testGetById() {
        CriteriaSupervisorShowDto criteriaSupervisorShowDto = CriteriaSupervisorShowDto.builder().id(1L).build();
        Criteria criteria = Criteria.builder().id(1L).build();

        when(criteriaRepository.findById(1L)).thenReturn(Optional.of(criteria));

        Criteria result = criteriaService.findById(1L);

        assertNotNull(result);
        assertEquals(result, criteria);
        assertEquals(result.getId(), criteriaSupervisorShowDto.getId());
    }

    @Test
    void testCreate() {
        CriteriaSupervisorCreateDto createDto = CriteriaSupervisorCreateDto.builder()
                .coefficient(1)
                .description("Description")
                .section("Section")
                .zone("Zone")
                .build();
        Criteria criteria = Criteria.builder()
                .id(1L)
                .section(Section.builder().name("Section").build())
                .zone(Zone.builder().name("Zone").build())
                .description("Description")
                .build();
        when(sectionService.findByName("Section")).thenReturn(Section.builder().name("Section").build());
        when(zoneService.findByName("Zone")).thenReturn(Zone.builder().name("Zone").build());
        when(criteriaRepository.save(any(Criteria.class))).thenReturn(criteria);

        assertNotNull(criteriaService.create(createDto));
        assertEquals(criteria.getDescription(), createDto.getDescription());
    }

    @Test
    void getCriteriaByIdWhenIdExists() {
        when(criteriaRepository.findById(criteriaTest.getId())).thenReturn(Optional.of(criteriaTest));

        Criteria foundCriteria = criteriaService.getCriteriaById(criteriaTest.getId());

        assertEquals(criteriaTest.getId(), foundCriteria.getId());
        assertEquals(criteriaTest.getDescription(), foundCriteria.getDescription());

        verify(criteriaRepository, times(1)).findById(criteriaTest.getId());
    }

    @Test
    void getCriteriaByIdWhenIdDoesNotExist() {
        when(criteriaRepository.findById(2L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(NoSuchElementException.class, () ->  criteriaService.getCriteriaById(2L));

        String expectedMessage = "Критерия с ID: 2 не найдена!";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));

        verify(criteriaRepository, times(1)).findById(2L);
    }

    @Test
    void testGetByDescription_EmptyDescription() {
        List<CriteriaSupervisorShowDto> result = criteriaService.getByDescription("", true);

        assertNotNull(result);
        assertTrue(result.isEmpty());

        verify(criteriaRepository, never()).findCriterionByDescriptionContainingIgnoreCase(anyString());
    }


    @Test
    void testGetById_NotFound() {
        Long id = 1L;
        when(criteriaRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(NullPointerException.class, () -> criteriaService.getById(id));

        verify(criteriaRepository, times(1)).findById(id);
    }

    @Test
    void testFindById() {
        Long id = 1L;
        Criteria criteria = new Criteria();
        criteria.setId(id);

        when(criteriaRepository.findById(id)).thenReturn(Optional.of(criteria));

        Criteria result = criteriaService.findById(id);

        assertNotNull(result);
        assertEquals(id, result.getId());

        verify(criteriaRepository, times(1)).findById(id);
    }

    @Test
    void testFindById_NotFound() {
        Long id = 1L;
        when(criteriaRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> criteriaService.findById(id));

        verify(criteriaRepository, times(1)).findById(id);
    }

    @Test
    void testGetWowCriteria() {
        Criteria criteria = new Criteria();
        List<Criteria> criteriaList = List.of(criteria);

        CriteriaSupervisorShowDto dto = new CriteriaSupervisorShowDto();
        when(criteriaRepository.findCriteriaWhereSectionWow()).thenReturn(criteriaList);
        when(dtoBuilder.buildCriteriaSupervisorShowDto(criteria)).thenReturn(dto);

        List<CriteriaSupervisorShowDto> result = criteriaService.getWowCriteria();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(dto, result.get(0));

        verify(criteriaRepository, times(1)).findCriteriaWhereSectionWow();
        verify(dtoBuilder, times(1)).buildCriteriaSupervisorShowDto(criteria);
    }

    @Test
    void testGetCriteriaCriteria() {
        Criteria criteria = new Criteria();
        List<Criteria> criteriaList = List.of(criteria);

        CriteriaSupervisorShowDto dto = new CriteriaSupervisorShowDto();
        when(criteriaRepository.findCriteriaWhereSectionCrit()).thenReturn(criteriaList);
        when(dtoBuilder.buildCriteriaSupervisorShowDto(criteria)).thenReturn(dto);

        List<CriteriaSupervisorShowDto> result = criteriaService.getCritCriteria();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(dto, result.get(0));

        verify(criteriaRepository, times(1)).findCriteriaWhereSectionCrit();
        verify(dtoBuilder, times(1)).buildCriteriaSupervisorShowDto(criteria);
    }
}
