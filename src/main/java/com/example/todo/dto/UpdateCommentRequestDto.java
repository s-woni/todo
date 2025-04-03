package com.example.todo.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class UpdateCommentRequestDto {

    @NotNull(message = "새로운 댓글을 입력해주세요.")
    private final String newComment;

    public UpdateCommentRequestDto(String newComment) {
        this.newComment = newComment;
    }
}
