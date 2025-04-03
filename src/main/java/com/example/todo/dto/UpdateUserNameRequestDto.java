package com.example.todo.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class UpdateUserNameRequestDto {

    @NotBlank(message = "새로운 이름을 입력해주세요.")
    private final String newName;

    @NotBlank(message = "변경을 위한 기존 비밀번호를 입력해주세요.")
    private final String password;

    public UpdateUserNameRequestDto(String newName, String password) {
        this.newName = newName;
        this.password = password;
    }
}
