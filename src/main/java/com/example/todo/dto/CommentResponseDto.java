package com.example.todo.dto;

import com.example.todo.entity.Comment;
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

    public static CommentResponseDto toDto(Comment comment) {
        return new CommentResponseDto(comment.getId(), comment.getComments(), comment.getMember().getUsername());
    }
}
