package ua.korzh.testtask.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import ua.korzh.testtask.service.user.UserService;
import javax.transaction.Transactional;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class SecurityUserDetailsManager implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {

        var appUser = userService.getUserByUsername(s);
        Set<GrantedAuthority> authorities = appUser.getRoles().stream()
                .flatMap(role -> role.getGrantedAuthorities().stream())
                .collect(Collectors.toSet());

        return User.builder()
                .username(appUser.getUsername())
                .password(appUser.getPassword())
                .authorities(authorities)
                .build();
    }
}
