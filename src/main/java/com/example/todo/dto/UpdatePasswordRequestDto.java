package com.example.todo.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class UpdatePasswordRequestDto {

    @NotBlank(message = "기존 비밀번호를 입력해주세요.")
    private final String oldPassword;

    @NotBlank(message = "새로운 비밀번호를 입력해주세요.")
    private final String newPassword;

    public UpdatePasswordRequestDto(String oldPassword, String newPassword) {
        this.oldPassword = oldPassword;
        this.newPassword = newPassword;
    }
}
