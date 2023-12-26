package com.toy.AssistCook.controller;

import com.toy.AssistCook.service.member.MemberServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ApiController {
    private final MemberServiceImpl memberService;

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
}
