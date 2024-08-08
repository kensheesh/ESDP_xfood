package kg.attractor.xfood.service;

import kg.attractor.xfood.model.Shift;

import java.util.List;

public interface ShiftService {
    List<Shift> getShiftsByOpportunityId(long id);
}
