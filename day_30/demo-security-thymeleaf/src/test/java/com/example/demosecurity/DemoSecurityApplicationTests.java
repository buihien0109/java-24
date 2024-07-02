package com.example.demosecurity;

import com.example.demosecurity.entity.Role;
import com.example.demosecurity.entity.User;
import com.example.demosecurity.repository.RoleRepository;
import com.example.demosecurity.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@SpringBootTest
class DemoSecurityApplicationTests {
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    void save_roles() {
        Role userRole = Role.builder().name("USER").build();
        Role adminRole = Role.builder().name("ADMIN").build();
        roleRepository.save(userRole);
        roleRepository.save(adminRole);
    }

    @Test
    void save_users() {
        Role userRole = roleRepository
                .findByName("USER").orElse(null);
        Role adminRole = roleRepository
                .findByName("ADMIN").orElse(null);

        User user1 = User.builder()
                .name("hien")
                .email("hien@gmail.com")
                .password(passwordEncoder.encode("123"))
                .roles(List.of(userRole, adminRole))
                .build();
        userRepository.save(user1);
        
        User user2 = User.builder()
                .name("duy")
                .email("duy@gmail.com")
                .password(passwordEncoder.encode("123"))
                .roles(List.of(userRole))
                .build();
        userRepository.save(user2);
    }
}
