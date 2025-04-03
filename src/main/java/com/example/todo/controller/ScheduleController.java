package com.example.todo.controller;

import com.example.todo.common.Const;
import com.example.todo.dto.LoginResponseDto;
import com.example.todo.dto.ScheduleRequestDto;
import com.example.todo.dto.ScheduleResponseDto;
import com.example.todo.dto.UpdateScheduleRequestDto;
import com.example.todo.service.MemberService;
import com.example.todo.service.ScheduleService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/schedules")
@RequiredArgsConstructor
public class ScheduleController {

    private final MemberService memberService;
    private final ScheduleService scheduleService;

    @PostMapping
    public ResponseEntity<ScheduleResponseDto> save(@RequestBody ScheduleRequestDto requestDto, HttpServletRequest servletRequest) {

        LoginResponseDto loggedUser = (LoginResponseDto) servletRequest.getSession(false).getAttribute(Const.LOGIN_USER);

        ScheduleResponseDto scheduleResponseDto = scheduleService.save(requestDto.getTitle(), requestDto.getContents(), requestDto.getUsername(), loggedUser.getId());

        return new ResponseEntity<>(scheduleResponseDto, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<List<ScheduleResponseDto>> findAllByUserId(@PathVariable Long id) {

        List<ScheduleResponseDto> scheduleResponseDtoList = scheduleService.findByMemberId(id);

        return new ResponseEntity<>(scheduleResponseDtoList, HttpStatus.OK);
    }

    @GetMapping("/{userId}/{scheduleId}")
    public ResponseEntity<ScheduleResponseDto> findOneByUserId(@PathVariable Long userId, @PathVariable Long scheduleId) {

        ScheduleResponseDto scheduleResponseDto = scheduleService.findByMemberIdAndScheduleId(userId, scheduleId);

        return new ResponseEntity<>(scheduleResponseDto, HttpStatus.OK);
    }

    @PatchMapping("/{userId}/{scheduleId}")
    public ResponseEntity<Void> updateSchedule(@PathVariable Long userId, @PathVariable Long scheduleId, @RequestBody UpdateScheduleRequestDto requestDto) {

        scheduleService.updateSchedule(userId, scheduleId, requestDto.getNewTitle(), requestDto.getNewContents());

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{userId}/{scheduleId}")
    public ResponseEntity<Void> deleteOneByUserId(@PathVariable Long userId, @PathVariable Long scheduleId) {

        scheduleService.deleteByUserIdAndScheduleId(userId, scheduleId);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
