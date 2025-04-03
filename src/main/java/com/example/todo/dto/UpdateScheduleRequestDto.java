package com.example.todo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class UpdateScheduleRequestDto {

    @NotBlank(message = "새로운 제목을 입력해주세요.")
    private final String newTitle;

    @NotNull(message = "새로운 일정을 입력해주세요.")
    private final String newContents;

    public UpdateScheduleRequestDto(String newTitle, String newContents) {
        this.newTitle = newTitle;
        this.newContents = newContents;
    }
}
