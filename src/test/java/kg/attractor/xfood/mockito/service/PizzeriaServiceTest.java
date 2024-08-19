package kg.attractor.xfood.mockito.service;

import kg.attractor.xfood.dto.pizzeria.PizzeriaDto;
import kg.attractor.xfood.dto.pizzeria.PizzeriaShowDto;
import kg.attractor.xfood.dto.pizzeria.PizzeriaWeeklyDto;
import kg.attractor.xfood.exception.NotFoundException;
import kg.attractor.xfood.model.Pizzeria;
import kg.attractor.xfood.repository.PizzeriaRepository;
import kg.attractor.xfood.service.impl.DtoBuilder;
import kg.attractor.xfood.service.impl.PizzeriaServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class PizzeriaServiceTest {

    @Mock
    private PizzeriaRepository pizzeriaRepository;

    @Mock
    private DtoBuilder dtoBuilder;

    @InjectMocks
    private PizzeriaServiceImpl pizzeriaService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetPizzeriasByLocationIdEmptyList() {
        long locationId = 1L;
        when(pizzeriaRepository.findByLocation_Id(locationId)).thenReturn(Collections.emptyList());

        List<PizzeriaWeeklyDto> result = pizzeriaService.getPizzeriasByLocationId(locationId);

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void testGetPizzeriasByPartOfNameNoMatch() {
        String query = "nonexistent";
        when(pizzeriaRepository.findByNameContainingIgnoreCase(query)).thenReturn(Collections.emptyList());

        List<PizzeriaShowDto> result = pizzeriaService.getPizzeriasByPartOfName(query);

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void testGetPizzeriasByLocationId() {
        long locationId = 1L;
        Pizzeria pizzeria = new Pizzeria();
        List<Pizzeria> pizzerias = Collections.singletonList(pizzeria);

        when(pizzeriaRepository.findByLocation_Id(locationId)).thenReturn(pizzerias);
        when(dtoBuilder.buildPizzeriaWeeklyDtos(pizzerias)).thenReturn(Collections.singletonList(new PizzeriaWeeklyDto()));

        List<PizzeriaWeeklyDto> result = pizzeriaService.getPizzeriasByLocationId(locationId);

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(pizzeriaRepository).findByLocation_Id(locationId);
        verify(dtoBuilder).buildPizzeriaWeeklyDtos(pizzerias);
    }

    @Test
    void testAddInvalidDto() {
        PizzeriaDto dto = new PizzeriaDto();
        assertThrows(NullPointerException.class, () -> pizzeriaService.add(dto));
    }

    @Test
    void testEditInvalidDto() {
        PizzeriaDto dto = new PizzeriaDto();
        assertThrows(NullPointerException.class, () -> pizzeriaService.edit(dto));
    }


    @Test
    void testGetAllPizzerias() {
        Pizzeria pizzeria = new Pizzeria();
        List<Pizzeria> pizzerias = Collections.singletonList(pizzeria);

        when(pizzeriaRepository.findAll()).thenReturn(pizzerias);
        when(dtoBuilder.buildPizzeriaDto(pizzeria)).thenReturn(new PizzeriaDto());

        List<PizzeriaDto> result = pizzeriaService.getAllPizzerias();

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(pizzeriaRepository).findAll();
        verify(dtoBuilder).buildPizzeriaDto(pizzeria);
    }

    @Test
    void testGetPizzeriasByPartOfNameWithoutQuery() {
        List<Pizzeria> pizzerias = Collections.singletonList(new Pizzeria());

        when(pizzeriaRepository.findAll()).thenReturn(pizzerias);
        when(dtoBuilder.buildPizzeriaShowDtos(pizzerias)).thenReturn(Collections.singletonList(new PizzeriaShowDto()));

        List<PizzeriaShowDto> result = pizzeriaService.getPizzeriasByPartOfName("");

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(pizzeriaRepository).findAll();
        verify(dtoBuilder).buildPizzeriaShowDtos(pizzerias);
    }


    @Test
    void testPerformanceForGetPizzeriasByLocationId() {
        long locationId = 1L;
        List<Pizzeria> pizzerias = Collections.nCopies(1000, new Pizzeria());
        when(pizzeriaRepository.findByLocation_Id(locationId)).thenReturn(pizzerias);

        long startTime = System.currentTimeMillis();
        pizzeriaService.getPizzeriasByLocationId(locationId);
        long endTime = System.currentTimeMillis();

        long duration = endTime - startTime;
        assertTrue(duration < 1000);
    }

    @Test
    void testGetPizzeriaDtoById() {
        long id = 1L;
        Pizzeria pizzeria = new Pizzeria();

        when(pizzeriaRepository.findById(id)).thenReturn(Optional.of(pizzeria));
        when(dtoBuilder.buildPizzeriaDto(pizzeria)).thenReturn(new PizzeriaDto());

        PizzeriaDto result = pizzeriaService.getPizzeriaDtoById(id);

        assertNotNull(result);
        verify(pizzeriaRepository).findById(id);
        verify(dtoBuilder).buildPizzeriaDto(pizzeria);
    }

    @Test
    void testGetPizzeriaDtoByIdNotFound() {
        long id = 1L;

        when(pizzeriaRepository.findById(id)).thenReturn(Optional.empty());

        NotFoundException thrown = assertThrows(NotFoundException.class, () -> pizzeriaService.getPizzeriaDtoById(id));
        assertEquals("Pizzeria not found", thrown.getMessage());
    }
}