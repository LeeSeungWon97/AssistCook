package com.toy.AssistCook.domain.member;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;


@Data
public class MemberForm {
    private Long id;
    @NotBlank(message = "아이디를 입력해주세요")
    @Pattern(regexp = "^(?=.*[a-zA-Z])[a-zA-Z0-9]*$",message = "아이디는 영문 또는 영문과 숫자의 조합이어야 합니다.")
    private String loginId;
    @NotBlank(message = "비밀번호를 입력해주세요")
    @Size(min = 8, max = 15, message = "비밀번호는 8~15글자이어야 합니다.")
    @Pattern(regexp = "^(?=.*[a-zA-Z0-9!@#$%^&*()_+{}\\[\\]:;<>,.?~\\\\/-])",message = "비밀번호는 영문,숫자,특수문자의 조합이어야 합니다.")
    private String pw;
    @NotBlank(message = "이름을 입력해주세요")
    private String name;
    @NotBlank(message = "생년월일을 입력해주세요")
    private String birth;
    private String postcode;
    private String address;
    @NotBlank(message = "상세 주소를 입력해주세요")
    private String detailAddress;
    private String extraAddress;
    @NotBlank(message = "이메일을 입력해주세요")
    private String email;
    @NotBlank(message = "도메인을 입력해주세요")
    private String domain;
}
