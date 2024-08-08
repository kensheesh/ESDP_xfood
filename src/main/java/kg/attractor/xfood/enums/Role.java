package kg.attractor.xfood.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static kg.attractor.xfood.enums.Permission.*;

@Getter
@RequiredArgsConstructor
public enum Role implements GrantedAuthority {

    ADMIN(
            Set.of(
                    ADMIN_READ, ADMIN_UPDATE, ADMIN_DELETE, ADMIN_CREATE,
                    SUPERVISOR_READ, SUPERVISOR_UPDATE, SUPERVISOR_CREATE, SUPERVISOR_DELETE,
                    EXPERT_READ, EXPERT_UPDATE, EXPERT_CREATE, EXPERT_DELETE
            )
    ),

    SUPERVISOR(
            Set.of(
                    SUPERVISOR_READ, SUPERVISOR_UPDATE, SUPERVISOR_CREATE, SUPERVISOR_DELETE,
                    EXPERT_READ, EXPERT_UPDATE, EXPERT_CREATE, EXPERT_DELETE
            )
    ),

    EXPERT(
            Set.of(
                    EXPERT_READ, EXPERT_UPDATE, EXPERT_CREATE, EXPERT_DELETE
            )
    );

    private final Set<Permission> permissions;

    public List<SimpleGrantedAuthority> getAuthorities() {

        List<SimpleGrantedAuthority> authorities = getPermissions()
                .stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toList());

        authorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return authorities;
    }

    @Override
    public String getAuthority() {
        return "ROLE_" + this.name();
    }

    private String role;

    public String getRole() {
        return role;
    }
}
