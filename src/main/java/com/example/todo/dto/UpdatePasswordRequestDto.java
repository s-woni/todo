package com.example.todo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Getter
public class UpdatePasswordRequestDto {

    @NotBlank(message = "기존 비밀번호를 입력해주세요.")
    private final String oldPassword;

    // 8 ~ 16자, 영문 대소문자, 숫자, 특수문자의 비밀번호
    @Pattern(regexp = "^[a-zA-Z0-9!@#$%^&*]{8,16}$", message = "비밀번호는 8 ~ 16자의 영문 대소문자, 숫자, 특수문자로 이루어져야 합니다.")
    @NotBlank(message = "새로운 비밀번호를 입력해주세요.")
    private final String newPassword;

    public UpdatePasswordRequestDto(String oldPassword, String newPassword) {
        this.oldPassword = oldPassword;
        this.newPassword = newPassword;
    }
}
