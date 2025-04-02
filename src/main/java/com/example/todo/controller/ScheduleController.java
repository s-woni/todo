package com.example.todo.controller;

import com.example.todo.dto.ScheduleRequestDto;
import com.example.todo.dto.ScheduleResponseDto;
import com.example.todo.service.MemberService;
import com.example.todo.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/schedules")
@RequiredArgsConstructor
public class ScheduleController {

    private final MemberService memberService;
    private final ScheduleService scheduleService;

    @PostMapping
    public ResponseEntity<ScheduleResponseDto> save(@RequestBody ScheduleRequestDto requestDto) {

        ScheduleResponseDto scheduleResponseDto = scheduleService.save(requestDto.getTitle(), requestDto.getContents(), requestDto.getUsername(), requestDto.getUserId());

        return new ResponseEntity<>(scheduleResponseDto, HttpStatus.CREATED);
    }
}
