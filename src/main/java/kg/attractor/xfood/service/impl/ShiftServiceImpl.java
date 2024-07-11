package kg.attractor.xfood.service.impl;

import kg.attractor.xfood.model.Shift;
import kg.attractor.xfood.repository.ShiftRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ShiftServiceImpl {
    private final ShiftRepository shiftRepository;

    void deleteAllByIds (List<Long> ids) {
        shiftRepository.deleteAllById(ids);
    }

    void deleteAllByOpportunityId(Long id) {
        shiftRepository.deleteAllByOpportunityId (id);
    }

    void saveAll(List<Shift> filteredShifts) {
        shiftRepository.saveAll(filteredShifts);
    }

    List<Long> getAllByOpportunityId(Long id) {
        return shiftRepository.findAllIdsByOpportunityId(id);
    }
}
