package com.example.demosecurity.controller;

import com.example.demosecurity.model.response.VerifyResponse;
import com.example.demosecurity.security.IsUser;
import com.example.demosecurity.service.AuthService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class WebController {
    private final AuthService authService;

    public WebController(AuthService authService) {
        this.authService = authService;
    }

    @GetMapping("/")
    public String getHome() {
        return "index";
    }

    @IsUser
    @GetMapping("/user")
    public String getUser() {
        return "user";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin")
    public String getAdmin() {
        return "admin";
    }

    @GetMapping("/login")
    public String getLogin() {
        return "login";
    }

    @GetMapping("/register")
    public String getRegister() {
        return "register";
    }

    // http://localhost:8080/xac-thuc-tai-khoan?token=e35bcf61-033f-4162-8321-7110cc3ba231
    @GetMapping("/xac-thuc-tai-khoan")
    public String getVerifyAccount(@RequestParam String token, Model model) {
        VerifyResponse response = authService.verifyAccount(token);
        model.addAttribute("response", response);
        return "verify-account";
    }
}
