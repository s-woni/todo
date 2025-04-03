package com.example.todo.dto;

import lombok.Getter;

@Getter
public class CommentRequestDto {

    private final String comment;

    private final String username;

    private final String userId;

    public CommentRequestDto(String comment, String username, String userId) {
        this.comment = comment;
        this.username = username;
        this.userId = userId;
    }
}
