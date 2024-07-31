package kg.attractor.xfood.dto.user;


import kg.attractor.xfood.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private Long id;
    private String name;
    private String surname;
    private String tgLink;
    private String email;
    private String password;
    private Boolean enabled = true;
    private Role role;
}
