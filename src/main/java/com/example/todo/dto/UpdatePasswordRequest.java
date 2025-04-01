package com.example.todo.dto;

import lombok.Getter;

@Getter
public class UpdatePasswordRequest {

    private final String oldPassword;

    private final String newPassword;

    public UpdatePasswordRequest(String oldPassword, String newPassword) {
        this.oldPassword = oldPassword;
        this.newPassword = newPassword;
    }
}
