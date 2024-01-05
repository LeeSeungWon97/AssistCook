package com.toy.AssistCook.repository.member;

import com.toy.AssistCook.domain.email.CodeStatus;
import com.toy.AssistCook.domain.email.EmailVerification;
import com.toy.AssistCook.domain.member.Member;
import com.toy.AssistCook.repository.mybatis.EmailVerificationMapper;
import com.toy.AssistCook.repository.mybatis.MemberMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
@RequiredArgsConstructor
public class MybatisMemberRepository implements MemberRepository{

    private final MemberMapper memberMapper;
    private final EmailVerificationMapper emailVerificationMapper;
    @Override
    public void save(Member member) {
        log.info("mybatis 회원 가입 시도");
        memberMapper.save(member);
        log.info("mybatis 회원 가입 완료");
        EmailVerification emailVerification = emailVerificationMapper.findByEmail(member.getEmail());
        emailVerificationMapper.updateStatus(emailVerification.getId(), CodeStatus.USED);
    }

    public Member findById(String loginId) {
        log.info("회원 아이디 검색");
        return memberMapper.findById(loginId);
    }
}
