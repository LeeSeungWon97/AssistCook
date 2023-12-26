package com.toy.AssistCook.domain.member;

import lombok.Data;


@Data
public class MemberForm {
    private Long id;
    private String login_id;
    private String pw;
    private String name;
    private String birth;
    private String postcode;
    private String address;
    private String detailAddress;
    private String extraAddress;
    private String email;
    private String domain;
}
