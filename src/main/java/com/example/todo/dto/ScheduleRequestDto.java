package com.example.todo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class ScheduleRequestDto {

    @NotBlank(message = "제목을 입력해주세요.")
    private final String title;

    @NotNull(message = "일정을 입력해주세요.")
    private final String contents;

    public ScheduleRequestDto(String title, String contents) {
        this.title = title;
        this.contents = contents;
    }
}
