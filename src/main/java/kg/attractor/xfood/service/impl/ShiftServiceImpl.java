package kg.attractor.xfood.service.impl;

import kg.attractor.xfood.model.Shift;
import kg.attractor.xfood.repository.ShiftRepository;
import kg.attractor.xfood.service.ShiftService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ShiftServiceImpl implements ShiftService {
    private final ShiftRepository shiftRepository;
    @Override
    public List<Shift> getShiftsByOpportunityId(long id) {
        List<Shift> shifts = shiftRepository.findByOpportunity_IdOrderByStartTimeAsc(id);
        return shifts;
    }

    public void deleteAllByIds(List<Long> ids) {
        shiftRepository.deleteAllById(ids);
    }

    public void deleteAllByOpportunityId(Long id) {
        shiftRepository.deleteAllByOpportunityId (id);
    }

    public void saveAll(List<Shift> filteredShifts) {
        shiftRepository.saveAll(filteredShifts);
    }

    public List<Long> getAllByOpportunityId(Long id) {
        return shiftRepository.findAllIdsByOpportunityId(id);
    }
}
