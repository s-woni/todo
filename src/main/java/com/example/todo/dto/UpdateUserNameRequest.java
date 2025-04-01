package com.example.todo.dto;

import lombok.Getter;

@Getter
public class UpdateUserNameRequest {

    private final String newName;

    private final String password;

    public UpdateUserNameRequest(String newName, String password) {
        this.newName = newName;
        this.password = password;
    }
}
