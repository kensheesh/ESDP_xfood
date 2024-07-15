//package kg.attractor.xfood.mockito.service;
//
//import kg.attractor.xfood.dto.criteria.CriteriaSupervisorCreateDto;
//import kg.attractor.xfood.dto.criteria.CriteriaSupervisorShowDto;
//import kg.attractor.xfood.model.Criteria;
//import kg.attractor.xfood.model.Section;
//import kg.attractor.xfood.model.Zone;
//import kg.attractor.xfood.repository.CriteriaRepository;
//import kg.attractor.xfood.service.SectionService;
//import kg.attractor.xfood.service.ZoneService;
//import kg.attractor.xfood.service.impl.CriteriaServiceImpl;
//import kg.attractor.xfood.service.impl.DtoBuilder;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import java.util.Collections;
//import java.util.List;
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
//
//public class CriteriaServiceTest {
//    @InjectMocks
//    private CriteriaServiceImpl criteriaService;
//
//    @Mock
//    private CriteriaRepository criteriaRepository;
//    @Mock
//    private SectionService sectionService;
//    @Mock
//    private ZoneService zoneService;
//
//    @Mock
//    private DtoBuilder dtoBuilder;
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//    }
//
//    @Test
//    void testFIndById() {
//        Criteria criteria = Criteria.builder().id(1L).coefficient(1).description("Description").zone(Zone.builder().build()).section(Section.builder().build()).build();
//        when(criteriaRepository.findById(1L)).thenReturn(Optional.of(criteria));
//        Criteria result = criteriaService.findById(1L);
//        assertNotNull(result);
//        assertEquals(result, criteria);
//    }
//
//    @Test
//    void testGetByDescription() {
//        List<Criteria> criterionByDescription = List.of(Criteria.builder().description("Description").zone(Zone.builder().build()).build());
//        when(criteriaRepository.findCriterionByDescriptionContainingIgnoreCase("Description")).thenReturn(criterionByDescription);
//
//        List<Criteria> foundByDescription = criteriaRepository.findCriterionByDescriptionContainingIgnoreCase("Description");
//
//        assertNotNull(foundByDescription);
//        assertEquals(foundByDescription, criterionByDescription);
//        assertEquals(foundByDescription.size(), criterionByDescription.size());
//    }
//
//    @Test
//    void testGetById(){
//        CriteriaSupervisorShowDto criteriaSupervisorShowDto = CriteriaSupervisorShowDto.builder().id(1L).build();
//        Criteria criteria = Criteria.builder().id(1L).build();
//
//        when(criteriaRepository.findById(1L)).thenReturn(Optional.of(criteria));
//
//        Criteria result = criteriaService.findById(1L);
//
//        assertNotNull(result);
//        assertEquals(result, criteria);
//        assertEquals(result.getId(), criteriaSupervisorShowDto.getId());
//    }
//
//    @Test
//    void testCreate(){
//        CriteriaSupervisorCreateDto createDto = CriteriaSupervisorCreateDto.builder()
//                .coefficient(1)
//                .description("Description")
//                .section("Section")
//                .zone("Zone")
//                .build();
//        Criteria criteria = Criteria.builder()
//                .id(1L)
//                .section(Section.builder().name("Section").build())
//                .zone(Zone.builder().name("Zone").build())
//                .description("Description")
//                .build();
//        when(sectionService.findByName("Section")).thenReturn(Section.builder().name("Section").build());
//        when(zoneService.findByName("Zone")).thenReturn(Zone.builder().name("Zone").build());
//        when(criteriaRepository.save(any(Criteria.class))).thenReturn(criteria);
//
//        assertNotNull(criteriaService.create(createDto));
//        assertEquals(criteria.getDescription(), createDto.getDescription());
//    }
//
//    @Test
//    void testGetByCheckTypeAndPizzeria(){
//        Criteria criteria = Criteria.builder()
//                .id(1L)
//                .description("Description")
//                .zone(Zone.builder().id(1L).name("Zone").build())
//                .section(Section.builder().id(1L).name("Section").build())
//                .coefficient(1)
//                .build();
//        CriteriaSupervisorShowDto dto =CriteriaSupervisorShowDto.builder().id(1L).build(); ;
//        List<CriteriaSupervisorShowDto> criteriaSupervisorShowDtos = List.of(dto);
////        when(criteriaRepository.findCriteriaByCriteriaTypeAndCriteriaPizzeria(1L, 1L)).thenReturn(List.of(criteria));
//        when(criteriaRepository.findCriteriaByCriteriaType(1L)).thenReturn(List.of(criteria));
//        when(dtoBuilder.buildCriteriaSupervisorShowDto(criteria)).thenReturn(dto);
//
//        List <CriteriaSupervisorShowDto> criterion = criteriaService.getByCheckTypeAndPizzeria(1L, 1L);
//        assertNotNull(criterion);
//        assertEquals(criterion, criteriaSupervisorShowDtos);
//    }
//
//    @Test
//    void testGetByCheckTypeAndPizzeria_CriteriaNotFoundForPizzeriaButFoundForCheckType() {
//        Criteria criteria = Criteria.builder()
//                .id(1L)
//                .description("Description")
//                .zone(Zone.builder().id(1L).name("Zone").build())
//                .section(Section.builder().id(1L).name("Section").build())
//                .coefficient(1)
//                .build();
//        CriteriaSupervisorShowDto dto = CriteriaSupervisorShowDto.builder().id(1L).build();
//        List<CriteriaSupervisorShowDto> criteriaSupervisorShowDtos = List.of(dto);
//
//
////        when(criteriaRepository.findCriteriaByCriteriaTypeAndCriteriaPizzeria(1L, 1L)).thenReturn(Collections.emptyList());
//        when(criteriaRepository.findCriteriaByCriteriaType(1L)).thenReturn(List.of(criteria));
//        when(dtoBuilder.buildCriteriaSupervisorShowDto(criteria)).thenReturn(dto);
//
//        List<CriteriaSupervisorShowDto> criterion = criteriaService.getByCheckTypeAndPizzeria(1L, 1L);
//
//        assertNotNull(criterion);
//        assertEquals(criterion, criteriaSupervisorShowDtos);
//
//    }
//
//    @Test
//    void testGetByCheckTypeAndPizzeria_CriteriaNotFound() {
//        when(criteriaRepository.findCriteriaByCriteriaTypeAndCriteriaPizzeria(1L, 1L)).thenReturn(Collections.emptyList());
//        when(criteriaRepository.findCriteriaByCriteriaType(1L)).thenReturn(Collections.emptyList());
//
//        List<CriteriaSupervisorShowDto> criterion = criteriaService.getByCheckTypeAndPizzeria(1L, 1L);
//
//        assertNotNull(criterion);
//        assertTrue(criterion.isEmpty());
//
//    }
//}
