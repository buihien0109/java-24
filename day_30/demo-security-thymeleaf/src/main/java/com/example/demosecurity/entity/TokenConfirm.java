package com.example.demosecurity.entity;

import com.example.demosecurity.model.enums.TokenType;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "token_confirms")
public class TokenConfirm {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    String token; // chuỗi token

    @Enumerated(EnumType.STRING)
    TokenType type; // loại token

    LocalDateTime createdAt; // thời gian tạo
    LocalDateTime expiredAt; // thời gian hết hạn
    LocalDateTime confirmedAt; // thời gian xác thực


    @ManyToOne
    @JoinColumn(name = "user_id")
    User user; // token này của user nào
}
