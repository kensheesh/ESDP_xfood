package kg.attractor.xfood.service;

import kg.attractor.xfood.dto.auth.RegisterUserDto;
import kg.attractor.xfood.dto.expert.ExpertShowDto;
import kg.attractor.xfood.dto.user.UserDto;
import kg.attractor.xfood.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserService {
    
    UserDto getUserDto();

    List<UserDto> getAllExpertsDtos();

    List<ExpertShowDto> fetchAllExperts();

    User findById(Long expertId);
  
    Page<UserDto> getAllUsers(String role, Pageable pageable, String word);
    
    void register(RegisterUserDto dto);
    
    Boolean isUserExist(String email);
}
