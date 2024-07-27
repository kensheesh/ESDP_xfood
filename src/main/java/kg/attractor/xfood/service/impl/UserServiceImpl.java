package kg.attractor.xfood.service.impl;

import kg.attractor.xfood.AuthParams;
import kg.attractor.xfood.dto.auth.RegisterUserDto;
import kg.attractor.xfood.dto.checklist.CheckListRewardDto;
import kg.attractor.xfood.dto.expert.ExpertShowDto;
import kg.attractor.xfood.dto.user.ExpertRewardDto;
import kg.attractor.xfood.dto.user.UserDto;
import kg.attractor.xfood.enums.Role;
import kg.attractor.xfood.exception.NotFoundException;
import kg.attractor.xfood.model.CheckList;
import kg.attractor.xfood.model.User;
import kg.attractor.xfood.repository.UserRepository;
import kg.attractor.xfood.service.CheckListService;
import kg.attractor.xfood.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder encoder;
    private final DtoBuilder dtoBuilder;
    @Lazy
    private final CheckListService checkListService;

    public void register(RegisterUserDto dto) {
        if (userRepository.existsByEmail(dto.getEmail())) throw new IllegalArgumentException("User already exists");
        User user = User.builder()
                .email(dto.getEmail())
                .password(encoder.encode(dto.getPassword()))
                .name(dto.getName())
                .surname(dto.getSurname())
                .role(dto.getRole())
                .phoneNumber(dto.getPhoneNumber())
                .build();
        userRepository.save(user);
    }

    @Override
    public UserDto getUserDto() {
        return dtoBuilder.buildUserDto(
                userRepository.findByEmail(AuthParams.getPrincipal().getUsername())
                        .orElseThrow(() -> new NotFoundException("User not found"))
        );
    }

    public User getByEmail(String name) {
        return userRepository.getByEmail(name)
                .orElseThrow(() -> new NotFoundException("User not found"));
    }


    @Override
    public List<UserDto> getAllExperts() {
        return userRepository.findByRole(Role.EXPERT.toString())
                .stream()
                .map(dtoBuilder::buildUserDto)
                .toList();
    }

    @Override
    public List<ExpertShowDto> fetchAllExperts() {
        List<UserDto> users = getAllExperts();
        List<ExpertShowDto> expertDtos = users.stream()
                .map(user -> dtoBuilder.buildExpertShowDto(user))
                .collect(Collectors.toList());
        return expertDtos;
    }

    @Override
    public User findById(Long expertId) {
        return userRepository.findById(expertId).orElseThrow(() -> new NotFoundException("Expert not found"));
    }

    @Override
    public ExpertRewardDto getExpertReward(LocalDate startDate, LocalDate endDate) {
        String expertEmail = AuthParams.getPrincipal().getUsername();
        List<CheckListRewardDto> rewardDtoList = checkListService.getChecklistRewardsByExpert(expertEmail, startDate, endDate);
        Double sumRewards = rewardDtoList.stream()
                .mapToDouble(CheckListRewardDto::getSumRewards)
                .sum();

       return ExpertRewardDto.builder()
               .checkListRewards(rewardDtoList)
               .sumRewards(sumRewards)
               .countChecks((long) rewardDtoList.size())
               .build();
    }

    private List<CheckListRewardDto> filterByDateRewards(List<CheckListRewardDto> checkLists, LocalDate startDate, LocalDate endDate) {
        if (startDate != null && endDate != null) {
            return checkLists.stream()
                    .filter(checkList -> {
                        LocalDate date = LocalDate.parse(checkList.getEndDate());
                        return (date.isEqual(startDate) || date.isAfter(startDate) || date.isBefore(endDate));
                    })
                    .toList();
        }
        return checkLists;
    }


    @Override
    public Object getRewards(String pizzeria, String expert, LocalDate startDate, LocalDate endDate) {
        return null;
    }

    public List<User> findSupervisors() {
        return userRepository.findByRole(Role.SUPERVISOR.toString());
    }
}