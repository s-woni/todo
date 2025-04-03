package com.example.todo.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;

@Getter
public class CommentRequestDto {

    @NotEmpty(message = "댓글을 입력해주세요.")
    private final String comment;

    public CommentRequestDto(String comment) {
        this.comment = comment;
    }
}
