package com.toy.AssistCook.domain.email;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class EmailVerification {
    private Long id;
    private String email;
    private String verificationCode;
    private LocalDateTime expirationTime;
    private CodeStatus status;
}
