package com.example.todo.dto;

import lombok.Getter;

@Getter
public class MemberResponseDto {

    private final String username;

    private final String email;

    public MemberResponseDto(String username, String email) {
        this.username = username;
        this.email = email;
    }
}
