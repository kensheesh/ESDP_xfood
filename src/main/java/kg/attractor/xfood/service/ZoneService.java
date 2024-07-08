package kg.attractor.xfood.service;

import kg.attractor.xfood.dto.ZoneSupervisorShowDto;
import kg.attractor.xfood.model.Zone;

import java.util.List;

public interface ZoneService {
    List<ZoneSupervisorShowDto> getZones();

    Zone findByName(String zone);
}
