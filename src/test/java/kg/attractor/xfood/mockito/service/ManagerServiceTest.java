package kg.attractor.xfood.mockito.service;

import kg.attractor.xfood.model.Manager;
import kg.attractor.xfood.repository.ManagerRepository;
import kg.attractor.xfood.service.ManagerService;
import kg.attractor.xfood.service.impl.ManagerServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ManagerServiceTest {
    @InjectMocks
    private ManagerServiceImpl managerService;
    @Mock
    private ManagerRepository managerRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindById(){
        Manager manager = Manager.builder().id(1L).name("Manager").build();
        when(managerRepository.findById(1L)).thenReturn(Optional.of(manager));
        Manager found = managerService.findById(1L);
        assertNotNull(found);
        assertEquals(manager, found);
    }

    @Test
    void testFindByIdThrowsException(){
        long id = -1L;
        NoSuchElementException e = assertThrows(NoSuchElementException.class, () -> managerService.findById(id));
        assertEquals("Manager not found by id: " + id, e.getMessage());
    }
}
