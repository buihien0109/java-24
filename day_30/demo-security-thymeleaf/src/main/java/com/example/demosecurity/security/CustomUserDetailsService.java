package com.example.demosecurity.security;

import com.example.demosecurity.model.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final List<User> users = new ArrayList<>();

    public CustomUserDetailsService(PasswordEncoder passwordEncoder) {
        users.add(new User(1, "Hien", "hien@gmail.com",
                passwordEncoder.encode("123"), List.of("USER", "ADMIN")));

        users.add(new User(2, "Duy", "duy@gmail.com",
                passwordEncoder.encode("123"), List.of("USER")));
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return users.stream()
                .filter(user -> user.getEmail().equals(email))
                .findFirst()
                .map(CustomUserDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}
