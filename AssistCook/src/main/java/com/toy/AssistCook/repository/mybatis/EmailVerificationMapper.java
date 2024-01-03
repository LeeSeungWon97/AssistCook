package com.toy.AssistCook.repository.mybatis;

import com.toy.AssistCook.domain.email.CodeStatus;
import com.toy.AssistCook.domain.email.EmailVerification;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface EmailVerificationMapper {
    void save(EmailVerification emailVerification);

    EmailVerification findByEmail(String email);

    int updateStatus(@Param("id") Long id, @Param("status") CodeStatus codeStatus);
}
