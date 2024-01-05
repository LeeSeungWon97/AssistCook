package com.toy.AssistCook.service.member;

import com.toy.AssistCook.domain.member.Grade;
import com.toy.AssistCook.domain.member.Member;
import com.toy.AssistCook.domain.member.MemberForm;
import com.toy.AssistCook.repository.member.MybatisMemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MybatisMemberRepository memberRepository;

    @Override
    public void save(MemberForm memberForm) {
        log.info("회원 가입 요청");
        Member member = transferMember(memberForm);
        memberRepository.save(member);
    }

    private Member transferMember(MemberForm memberForm) {
        Member member = new Member();
        String address = buildFullAddress(memberForm);
        String email = buildFullEmail(memberForm);
        member.setLoginId(memberForm.getLoginId());
        member.setPw(memberForm.getPw());
        member.setName(memberForm.getName());
        member.setBirth(memberForm.getBirth());
        member.setAddress(address);
        member.setEmail(email);
        member.setProfile("");
        member.setGrade(Grade.BASIC);
        return member;
    }

    private String buildFullAddress(MemberForm memberForm) {
        return String.format("(%s) %s %s%s",
                memberForm.getPostcode(), memberForm.getAddress(),
                memberForm.getDetailAddress(), memberForm.getExtraAddress());
    }

    private String buildFullEmail(MemberForm memberForm) {
        return memberForm.getEmail() + "@" + memberForm.getDomain();
    }

    public boolean findMemberById(String loginId) {
        Member findMember = memberRepository.findById(loginId);
        log.info("회원 검색 결과: {}", findMember);
        if (findMember != null) {
            log.info("이미 사용 중인 아이디");
            return false;
        } else {
            log.info("사용 할 수 있는 아이디");
            return true;
        }
    }
}
