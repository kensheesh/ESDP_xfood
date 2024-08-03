package kg.attractor.xfood.dto.user;

import kg.attractor.xfood.enums.Role;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserEditDto {

    private Long id;
    private String name;
    private String surname;
    private Boolean enabled;
    private Role role;

}
