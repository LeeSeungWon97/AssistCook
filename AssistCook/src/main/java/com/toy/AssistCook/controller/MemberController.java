package com.toy.AssistCook.controller;

import com.toy.AssistCook.domain.member.MemberForm;
import com.toy.AssistCook.service.member.MemberServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {

    private final MemberServiceImpl memberService;

    @GetMapping("new")
    public String joinForm() {
        log.info("회원 가입 페이지 이동 요청");
        return "members/joinForm";
    }

    @PostMapping("new")
    public String joinMember(@ModelAttribute MemberForm memberForm) {
        log.info("요청 데이터={}", memberForm);
        memberService.save(memberForm);
        return "redirect:/";
    }
}
