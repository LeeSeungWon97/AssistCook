package com.toy.AssistCook.repository.email;

import com.toy.AssistCook.domain.email.CodeStatus;
import com.toy.AssistCook.domain.email.EmailVerification;
import com.toy.AssistCook.repository.mybatis.EmailVerificationMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
@RequiredArgsConstructor
public class MyBatisEmailVerificationRepository {

    private final EmailVerificationMapper emailVerificationMapper;

    public void save(EmailVerification emailVerification) {
        // 이미 사용가능한 인증 번호가 있는지 확인
        log.info(emailVerification.getEmail());
        EmailVerification findResult = emailVerificationMapper.findByEmail(emailVerification.getEmail());
        log.info("findResult: {}",findResult);
        if (findResult != null) {
            updateStatus(findResult.getId(), CodeStatus.DISCARD);
        }
        emailVerificationMapper.save(emailVerification);
        log.info("인증 번호 저장 완료");
    }

    public EmailVerification findByEmail(String email) {
        log.info("email: {}",email);
        return emailVerificationMapper.findByEmail(email);
    }

    public void updateStatus(Long id, CodeStatus codeStatus) {
        log.info("id: {} / codeStatus: {}", id, codeStatus);
        emailVerificationMapper.updateStatus(id, codeStatus);
    }
}
