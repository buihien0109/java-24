package com.example.demosecurity.service;

import com.example.demosecurity.entity.Role;
import com.example.demosecurity.entity.TokenConfirm;
import com.example.demosecurity.entity.User;
import com.example.demosecurity.exception.BadRequestException;
import com.example.demosecurity.model.enums.TokenType;
import com.example.demosecurity.model.request.LoginRequest;
import com.example.demosecurity.model.request.RegisterRequest;
import com.example.demosecurity.model.response.VerifyResponse;
import com.example.demosecurity.repository.RoleRepository;
import com.example.demosecurity.repository.TokenConfirmRepository;
import com.example.demosecurity.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final HttpSession session;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenConfirmRepository tokenConfirmRepository;
    private final MailService mailService;

    public void login(LoginRequest request) {
        // Tạo đối tượng xác thực
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                request.getEmail(),
                request.getPassword()
        );

        try {
            // Tiến hành xác thực
            Authentication authentication = authenticationManager.authenticate(token);

            // Lưu đối tượng đã xác thực vào trong SecurityContextHolder
            SecurityContextHolder.getContext().setAuthentication(authentication);

            // Lưu vào trong session
            session.setAttribute("MY_SESSION", authentication.getName()); // Lưu email -> session
        } catch (DisabledException e) {
            throw new BadRequestException("Tài khoản của bạn chưa được kích hoạt. Vui lòng kiểm tra email của bạn");
        } catch (AuthenticationException e) {
            throw new BadRequestException("Tài khoản hoặc mật khẩu không đúng");
        }
    }

    public void register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new BadRequestException("Email đã tồn tại");
        }

        Role useRole = roleRepository.findByName("USER")
                .orElseThrow(() -> new BadRequestException("Không tìm thấy role USER"));

        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .roles(List.of(useRole))
                .enabled(false)
                .build();
        userRepository.save(user);

        TokenConfirm tokenConfirm = TokenConfirm.builder()
                .token(UUID.randomUUID().toString())
                .type(TokenType.CONFIRM_REGISTRATION)
                .createdAt(LocalDateTime.now())
                .expiredAt(LocalDateTime.now().plusDays(1))
                .user(user)
                .build();
        tokenConfirmRepository.save(tokenConfirm);

        String link = "http://localhost:8080/xac-thuc-tai-khoan?token=" + tokenConfirm.getToken();
//        mailService.sendMail(user.getEmail(), "Xác thực tài khoản", link);
        mailService.sendMail2(user, "Xác thực tài khoản", link);
    }

    public VerifyResponse verifyAccount(String token) {
        Optional<TokenConfirm> optionalTokenConfirm = tokenConfirmRepository
                .findByTokenAndType(token, TokenType.CONFIRM_REGISTRATION);

        if (optionalTokenConfirm.isEmpty()) {
            return VerifyResponse.builder()
                    .success(false)
                    .message("Token không hợp lệ")
                    .build();
        }

        TokenConfirm tokenConfirm = optionalTokenConfirm.get();
        if (tokenConfirm.getConfirmedAt() != null) {
            return VerifyResponse.builder()
                    .success(false)
                    .message("Token đã được xác thực")
                    .build();
        }

        if (tokenConfirm.getExpiredAt().isBefore(LocalDateTime.now())) {
            return VerifyResponse.builder()
                    .success(false)
                    .message("Token đã hết hạn")
                    .build();
        }

        User user = tokenConfirm.getUser();
        user.setEnabled(true);
        userRepository.save(user);

        tokenConfirm.setConfirmedAt(LocalDateTime.now());
        tokenConfirmRepository.save(tokenConfirm);

        return VerifyResponse.builder()
                .success(true)
                .message("Xác thực tài khoản thành công")
                .build();
    }
}
