package com.example.movieapp.rest;

import com.example.movieapp.model.request.UpdatePasswordRequest;
import com.example.movieapp.model.request.UpdateProfileUserRequest;
import com.example.movieapp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/users")
@RequiredArgsConstructor
public class UserApi {
    private final UserService userService;

    @PutMapping("/update-profile")
    ResponseEntity<?> updateProfile(@RequestBody UpdateProfileUserRequest request) {
        userService.updateProfile(request);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/update-password")
    ResponseEntity<?> updatePassword(@RequestBody UpdatePasswordRequest request) {
        userService.updatePassword(request);
        return ResponseEntity.ok().build();
    }
}
