package com.toy.AssistCook.controller;

import com.toy.AssistCook.service.email.EmailServiceImpl;
import com.toy.AssistCook.service.member.MemberServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ApiController {
    private final MemberServiceImpl memberService;
    private final EmailServiceImpl emailService;

    @GetMapping("/idDuplication")
    public ResponseEntity<Map<String, String>> isIdDuplication(@RequestParam("loginId") String loginId) {
        log.info("아이디 중복 체크 요청");
        boolean isDuplicate = memberService.findMemberById(loginId);
        Map<String, String> response = new HashMap<>();
        if (isDuplicate) {
            response.put("message", "사용 가능한 아이디 입니다.");
            return ResponseEntity.ok(response);
        } else {
            response.put("message", "이미 사용 중인 아이디 입니다.");
            return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
        }
    }

    @PostMapping("/requestEmailVerificationCode")
    public ResponseEntity<Map<String, String>> requestEmailVerificationCode(@RequestBody String email) {
        log.info("인증코드 요청 : {}", email);
        emailService.sendEmail(email);
        Map<String, String> response = new HashMap<>();
        response.put("message", "통신 완료");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/checkVerificationCode")
    public ResponseEntity<Map<String, String>> requestCheckVerificationCode(
            @RequestParam("checkCode") String inputCode, @RequestParam("requestEmail") String email) {
        log.info("인증 확인 요청: {}", inputCode);
        Map<String, String> response = new HashMap<>();
        String result = emailService.checkVerificationCode(email, inputCode);
        log.info("인증 결과: {}", result);
        if (result.equals("ACTIVE")) {
            response.put("message", "인증 완료");
            return ResponseEntity.ok(response);
        } else if (result.equals("EXPIRED")) {
            response.put("message", "유효 기간 만료");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        } else {
            response.put("message", "잘못된 인증 번호");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
    }
}
