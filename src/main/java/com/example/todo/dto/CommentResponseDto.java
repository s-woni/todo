package com.example.todo.dto;

import lombok.Getter;

@Getter
public class CommentResponseDto {

    private final Long id;

    private final String comments;

    private final String username;

    public CommentResponseDto(Long id, String comments, String username) {
        this.id = id;
        this.comments = comments;
        this.username = username;
    }
}
