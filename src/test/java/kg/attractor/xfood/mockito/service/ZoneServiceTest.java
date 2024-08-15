package kg.attractor.xfood.mockito.service;

import kg.attractor.xfood.dto.ZoneSupervisorShowDto;
import kg.attractor.xfood.model.Zone;
import kg.attractor.xfood.repository.ZoneRepository;
import kg.attractor.xfood.service.impl.DtoBuilder;
import kg.attractor.xfood.service.impl.ZoneServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

class ZoneServiceTest {

    @InjectMocks
    private ZoneServiceImpl zoneService;

    @Mock
    private ZoneRepository zoneRepository;

    @Mock
    private DtoBuilder dtoBuilder;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetZones() {
        List<ZoneSupervisorShowDto> zones = List.of(ZoneSupervisorShowDto.builder().id(1L).name("Zone").build());
        Zone zone = Zone.builder().id(1L).name("Zone").build();
        ZoneSupervisorShowDto zoneDto = ZoneSupervisorShowDto.builder().id(1L).name("Zone").build();

        when(zoneRepository.findAll()).thenReturn(List.of(zone));
        when(dtoBuilder.buildZoneDto(any(Zone.class))).thenReturn(zoneDto);

        List<ZoneSupervisorShowDto> foundZones = zoneService.getZones();

        assertEquals(zones, foundZones);
        assertEquals(zones.size(), foundZones.size());
    }

    @Test
    void testFindByName() {
        Zone zone = Zone.builder().id(1L).name("Zone").build();

        when(zoneRepository.findByName("Zone")).thenReturn(Optional.of(zone));

        Zone foundZone = zoneService.findByName("Zone");

        assertNotNull(foundZone);
        assertEquals(zone, foundZone);
        assertEquals(zone.getName(), foundZone.getName());
    }

    @Test
    void testFindByName_ZoneDoesNotExist() {
        String zoneName = "ZoneTest";

        when(zoneRepository.findByName(zoneName)).thenReturn(Optional.empty());

        NoSuchElementException exception = assertThrows(NoSuchElementException.class, () -> zoneService.findByName(zoneName));

        assertEquals("Зона с названием " + zoneName + " не найдена", exception.getMessage());
    }
}
