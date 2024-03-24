package FullMetal.SpringSecurity.Models.Enumerate;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;


import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static FullMetal.SpringSecurity.Models.Enumerate.Permission.*;

@RequiredArgsConstructor
public enum Role {

    ADMIN(
        Set.of(
            ADMIN_READ,
            ADMIN_CREATE,
            ADMIN_UPDATE,
            ADMIN_DELETE,
            USER_READ,
            USER_CREATE,
            USER_UPDATE,
            USER_DELETE
        )),
    USER(
        Set.of(
            USER_READ,
            USER_CREATE,
            USER_UPDATE,
            USER_DELETE
        )
    );


    @Getter
    private final Set<Permission> permissions;

    public List<SimpleGrantedAuthority> getAuthority() {
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();

        for (Permission permission : getPermissions()) {
            authorities.add(new SimpleGrantedAuthority(permission.getPermission()));
        }

        authorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return authorities;
    }
}
