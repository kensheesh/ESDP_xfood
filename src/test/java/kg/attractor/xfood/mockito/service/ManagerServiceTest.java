package kg.attractor.xfood.mockito.service;

import kg.attractor.xfood.dto.manager.ManagerDto;
import kg.attractor.xfood.model.Manager;
import kg.attractor.xfood.repository.ManagerRepository;
import kg.attractor.xfood.service.impl.DtoBuilder;
import kg.attractor.xfood.service.impl.ManagerServiceImpl;
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

class ManagerServiceTest {
    @InjectMocks
    private ManagerServiceImpl managerService;

    @Mock
    private ManagerRepository managerRepository;

    @Mock
    private DtoBuilder dtoBuilder;

    @Mock
    private Manager manager;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindById() {
        Manager manager = Manager.builder().id(1L).name("Manager").build();
        when(managerRepository.findById(1L)).thenReturn(Optional.of(manager));
        Manager found = managerService.findById(1L);
        assertNotNull(found);
        assertEquals(manager, found);
    }

    @Test
    void testFindByIdThrowsException() {
        long id = -1L;
        NoSuchElementException e = assertThrows(NoSuchElementException.class, () -> managerService.findById(id));
        assertEquals("Manager not found by id: " + id, e.getMessage());
    }

    @Test
    void testGetAllManagers() {
        Manager manager1 = new Manager();
        Manager manager2 = new Manager();
        List<Manager> managers = List.of(manager1, manager2);
        ManagerDto managerDto1 = new ManagerDto();
        ManagerDto managerDto2 = new ManagerDto();

        when(managerRepository.findAll()).thenReturn(managers);
        when(dtoBuilder.buildManagerDto(manager1)).thenReturn(managerDto1);
        when(dtoBuilder.buildManagerDto(manager2)).thenReturn(managerDto2);

        List<ManagerDto> result = managerService.getAllManagers();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertTrue(result.contains(managerDto1));
        assertTrue(result.contains(managerDto2));
    }

    @Test
    void testGetAllManagersAscBySurname() {
        Manager manager1 = new Manager();
        Manager manager2 = new Manager();
        List<Manager> managers = List.of(manager1, manager2);
        ManagerDto managerDto1 = new ManagerDto();
        ManagerDto managerDto2 = new ManagerDto();

        when(managerRepository.findByOrderBySurnameAsc()).thenReturn(managers);
        when(dtoBuilder.buildManagerDto(manager1)).thenReturn(managerDto1);
        when(dtoBuilder.buildManagerDto(manager2)).thenReturn(managerDto2);

        List<ManagerDto> result = managerService.getAllManagersAscBySurname();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertTrue(result.contains(managerDto1));
        assertTrue(result.contains(managerDto2));
    }

    @Test
    void testFindById_Success() {
        Long id = 1L;
        when(managerRepository.findById(id)).thenReturn(Optional.of(manager));

        Manager result = managerService.findById(id);

        assertNotNull(result);
        assertEquals(manager, result);
    }

    @Test
    void testFindById_NotFound() {
        Long id = 1L;
        when(managerRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> managerService.findById(id));
    }

    @Test
    void testFindByPhoneNumber_Success() {
        String phoneNumber = "123456789";
        when(managerRepository.getByPhoneNumber(phoneNumber)).thenReturn(Optional.of(manager));

        Manager result = managerService.findByPhoneNumber(phoneNumber);

        assertNotNull(result);
        assertEquals(manager, result);
    }

    @Test
    void testFindByPhoneNumber_NotFound() {
        String phoneNumber = "123456789";
        when(managerRepository.getByPhoneNumber(phoneNumber)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> managerService.findByPhoneNumber(phoneNumber));
    }

    @Test
    void testGetManagerDtoById() {
        Long id = 1L;
        ManagerDto managerDto = new ManagerDto();
        when(managerRepository.findById(id)).thenReturn(Optional.of(manager));
        when(dtoBuilder.buildManagerDto(manager)).thenReturn(managerDto);

        ManagerDto result = managerService.getManagerDtoById(id);

        assertNotNull(result);
        assertEquals(managerDto, result);
    }

    @Test
    void testGetManagersByUuid() {
        String staffId = "staff-uuid";
        when(managerRepository.findByUuid(staffId)).thenReturn(manager);

        Manager result = managerService.getManagersByUuid(staffId);

        assertNotNull(result);
        assertEquals(manager, result);
    }

    @Test
    void testAddManager() {
        Manager newManager = new Manager();
        managerService.addManager(newManager);

        verify(managerRepository, times(1)).saveAndFlush(newManager);
    }
}