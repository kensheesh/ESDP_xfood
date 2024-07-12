package kg.attractor.xfood.service;

import kg.attractor.xfood.dto.appeal.AppealDto;
import kg.attractor.xfood.dto.appeal.CreateAppealDto;
import kg.attractor.xfood.dto.appeal.DataAppealDto;
import org.springframework.stereotype.Service;

@Service
public interface AppealService {
   
    Long create(DataAppealDto data);
    
    AppealDto findById(Long id);

    void update(CreateAppealDto createAppealDto, Long id);
}
