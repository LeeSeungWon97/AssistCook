package com.toy.AssistCook.domain.member;

import lombok.Data;

@Data
public class Member {
    private Long id;
    private String loginId;
    private String pw;
    private String name;
    private String birth;
    private String address;
    private String email;
    private String profile;
    private Grade grade;
}
