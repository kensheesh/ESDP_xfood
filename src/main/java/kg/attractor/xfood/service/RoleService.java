package kg.attractor.xfood.service;

import kg.attractor.xfood.enums.Role;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RoleService {
    List<Role> getRoles();
}
