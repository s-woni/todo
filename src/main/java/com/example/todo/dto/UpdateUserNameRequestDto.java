package com.example.todo.dto;

import lombok.Getter;

@Getter
public class UpdateUserNameRequestDto {

    private final String newName;

    private final String password;

    public UpdateUserNameRequestDto(String newName, String password) {
        this.newName = newName;
        this.password = password;
    }
}
