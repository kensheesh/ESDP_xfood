package kg.attractor.xfood.service.impl;

import kg.attractor.xfood.enums.Role;
import kg.attractor.xfood.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    @Override
    public List<Role> getRoles() {
        return List.of(Role.values());
    }
}
