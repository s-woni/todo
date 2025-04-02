package com.example.todo.dto;

import lombok.Getter;

@Getter
public class ScheduleRequestDto {

    private final String title;

    private final String contents;

    private final String username;

    private final Long userId;

    public ScheduleRequestDto(String title, String contents, String username, Long userId) {
        this.title = title;
        this.contents = contents;
        this.username = username;
        this.userId = userId;
    }
}
