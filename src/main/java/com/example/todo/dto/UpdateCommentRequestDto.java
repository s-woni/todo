package com.example.todo.dto;

import lombok.Getter;

@Getter
public class UpdateCommentRequestDto {

    private final String newComment;

    public UpdateCommentRequestDto(String newComment) {
        this.newComment = newComment;
    }
}
