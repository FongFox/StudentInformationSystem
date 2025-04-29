package vn.hsu.StudentInformationSystem.service.impl;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import vn.hsu.StudentInformationSystem.service.UserService;

import java.util.Collections;

@Component("userDetailsService")
public class UserDetailsCustomImpl implements UserDetailsService {

    private final UserService userService;

    public UserDetailsCustomImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // TODO Auto-generated method stub
        vn.hsu.StudentInformationSystem.model.User user = this.userService.handleFetchUserByUsername(username);

        return new User(
                user.getUsername(),
                user.getPassword(),
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"))
        );
    }

}
