package com.example.todo.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class DeleteMemberRequestDto {

    @NotBlank(message = "비밀번호를 입력해주세요.")
    private final String password;

    public DeleteMemberRequestDto(String password) {
        this.password = password;
    }
}
