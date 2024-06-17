package com.example.movieapp.service;

import com.example.movieapp.entity.User;
import com.example.movieapp.model.request.UpdatePasswordRequest;
import com.example.movieapp.model.request.UpdateProfileUserRequest;
import com.example.movieapp.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final HttpSession session;
    private final BCryptPasswordEncoder passwordEncoder;

    public void updateProfile(UpdateProfileUserRequest request) {
        User user = (User) session.getAttribute("currentUser");
        user.setName(request.getName());

        session.setAttribute("currentUser", user);
        userRepository.save(user);
    }

    public void updatePassword(UpdatePasswordRequest request) {
        User user = (User) session.getAttribute("currentUser");

        if (!passwordEncoder.matches(request.getOldPassword(), user.getPassword())) {
            throw new RuntimeException("Mật khẩu cũ không đúng");
        }

        if (!request.getNewPassword().equals(request.getConfirmPassword())) {
            throw new RuntimeException("Mật khẩu xác nhận không khớp");
        }

        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        session.setAttribute("currentUser", user);
        userRepository.save(user);
    }
}
