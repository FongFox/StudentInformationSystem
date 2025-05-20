package vn.hsu.StudentInformationSystem.service.impl;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import vn.hsu.StudentInformationSystem.model.Student;
import vn.hsu.StudentInformationSystem.service.StudentService;

import java.util.Collections;

@Component("userDetailsService")
public class UserDetailsCustomImpl implements UserDetailsService {
    private final StudentService studentService;

    public UserDetailsCustomImpl(StudentService studentService) {
        this.studentService = studentService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Student student;
        try {
            student = studentService.handleFetchStudentByUsername(username);
        } catch (EntityNotFoundException ex) {
            throw new UsernameNotFoundException("User with username/password not found");
        }

        return new User(
                student.getUsername(),
                student.getPassword(),
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"))
        );
    }
}
