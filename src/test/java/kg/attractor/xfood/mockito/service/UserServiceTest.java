package kg.attractor.xfood.mockito.service;

import kg.attractor.xfood.repository.UserRepository;
import kg.attractor.xfood.service.impl.UserServiceImpl;
import org.mockito.InjectMocks;
import org.mockito.Mock;

public class UserServiceTest {

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;

}
