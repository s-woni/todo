package com.example.todo.dto;

import lombok.Getter;

@Getter
public class UpdateScheduleRequestDto {

    private final String newTitle;

    private final String newContents;

    public UpdateScheduleRequestDto(String newTitle, String newContents) {
        this.newTitle = newTitle;
        this.newContents = newContents;
    }
}
