package kg.attractor.xfood.service.impl;

import kg.attractor.xfood.dto.ZoneSupervisorShowDto;
import kg.attractor.xfood.model.Zone;
import kg.attractor.xfood.repository.ZoneRepository;
import kg.attractor.xfood.service.ZoneService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Slf4j
@Service
@RequiredArgsConstructor
public class ZoneServiceImpl implements ZoneService {
    private final ZoneRepository zoneRepository;
    private final DtoBuilder dtoBuilder;
    @Override
    public List<ZoneSupervisorShowDto> getZones() {
        return zoneRepository.findAll().stream().map(dtoBuilder::buildZoneDto).toList();
    }
    @Override
    public Zone findByName(String zone) {
        return zoneRepository.findByName(zone).orElseThrow(()-> new NoSuchElementException("Зона с названием "+zone+" не найдена"));
    }
}
