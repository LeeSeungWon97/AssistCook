package com.toy.AssistCook.service.email;

import com.toy.AssistCook.domain.email.CodeStatus;
import com.toy.AssistCook.domain.email.EmailVerification;
import com.toy.AssistCook.repository.email.MyBatisEmailVerificationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailServiceImpl {

    private final JavaMailSender javaMailSender;
    private final MyBatisEmailVerificationRepository emailVerificationRepository;

    public void sendEmail(String email) {
        log.info("이메일 전송 시도");
        SimpleMailMessage message = new SimpleMailMessage();
        // 랜덤 코드 생성
        String code = createRandomCode();

        message.setTo(email);
        message.setSubject("AssistCook 회원가입 인증코드 입니다.");
        message.setText("인증코드: " + code);

        // 인증 코드 저장
        saveEmailVerification(email, code);
        javaMailSender.send(message);
        log.info("이메일 전송 완료");
    }

    private String createRandomCode() {
        log.info("인증 코드 생성");
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            int num = random.nextInt(3);
            switch (num) {
                // 대문자
                case 0:
                    sb.append((char) (random.nextInt(26) + 65));
                    break;
                // 소문자
                case 1:
                    sb.append((char) (random.nextInt(26) + 97));
                    break;
                // 숫자
                case 2:
                    sb.append((char) (random.nextInt(10) + 48));
                    break;
            }
        }
        log.info("인증코드 : {}", sb.toString());
        return sb.toString();
    }

    private void saveEmailVerification(String email, String code) {
        log.info("인증번호 DB 저장");
        EmailVerification emailVerification = new EmailVerification();
        emailVerification.setEmail(email.replace("\"",""));
        emailVerification.setVerificationCode(code);
        emailVerification.setExpirationTime(LocalDateTime.now().plusMinutes(3));    // 유효기간 설정
        emailVerificationRepository.save(emailVerification);
    }


    public String checkVerificationCode(String email, String inputCode) {
        EmailVerification findResult = emailVerificationRepository.findByEmail(email);
        // 이메일로 인증 코드를 요청한 데이터가 있는 경우
        if (findResult != null) {
            //인증 코드가 일치하는 경우
            if (findResult.getVerificationCode().equals(inputCode)) {
                // 유효 기간이 지나지 않은 경우
                if (LocalDateTime.now().isBefore(findResult.getExpirationTime())) {
                    return String.valueOf(CodeStatus.ACTIVE);
                } else {
                    // 유효 기간이 지난 경우
                    emailVerificationRepository.updateStatus(findResult.getId(), CodeStatus.EXPIRED);
                    return String.valueOf(CodeStatus.EXPIRED);
                }
            }
        }
        return "WRONG";
    }
}
