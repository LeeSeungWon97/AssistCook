package com.toy.AssistCook.controller;

import com.toy.AssistCook.domain.member.MemberForm;
import com.toy.AssistCook.service.member.MemberServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {

    private final MemberServiceImpl memberService;

    @GetMapping("new")
    public String joinForm(Model model) {
        log.info("회원 가입 페이지 이동 요청");
        if (!model.containsAttribute("memberForm")) {
            model.addAttribute("memberForm", new MemberForm());
        }
        return "members/joinForm";
    }

    @PostMapping("new")
    public String joinMember(@Valid @ModelAttribute MemberForm memberForm, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        log.info("요청 데이터={}", memberForm);
        if (bindingResult.hasErrors()) {
            log.info("error={}", bindingResult);
            redirectAttributes.addFlashAttribute("memberForm", memberForm);
            redirectAttributes.addFlashAttribute("error", "회원가입에 실패했습니다. 입력 정보를 확인하고 다시 요청해주십시오.");
            return "redirect:/members/new";
        }
        memberService.save(memberForm);
        return "redirect:/";
    }
}
