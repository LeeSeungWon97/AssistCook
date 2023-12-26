package com.toy.AssistCook.repository.mybatis;

import com.toy.AssistCook.domain.member.Member;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberMapper {
    void save(Member member);

    Member findById(String loginId);
}
