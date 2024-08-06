package kg.attractor.xfood.service;

import kg.attractor.xfood.dto.user.UserEditDto;
import org.springframework.stereotype.Service;

@Service
public interface AdminService {

    void deleteUser(Long id);

    void editUser(UserEditDto userEditDto);
}
