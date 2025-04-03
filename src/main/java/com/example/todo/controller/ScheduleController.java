package com.example.todo.controller;

import com.example.todo.common.Const;
import com.example.todo.dto.LoginResponseDto;
import com.example.todo.dto.ScheduleRequestDto;
import com.example.todo.dto.ScheduleResponseDto;
import com.example.todo.dto.UpdateScheduleRequestDto;
import com.example.todo.service.MemberService;
import com.example.todo.service.ScheduleService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/schedules")
@RequiredArgsConstructor
public class ScheduleController {

    private final MemberService memberService;
    private final ScheduleService scheduleService;

    @PostMapping
    public ResponseEntity<ScheduleResponseDto> save(@Valid @RequestBody ScheduleRequestDto requestDto, HttpServletRequest servletRequest) {

        LoginResponseDto loggedUser = (LoginResponseDto) servletRequest.getSession(false).getAttribute(Const.LOGIN_USER);

        ScheduleResponseDto scheduleResponseDto = scheduleService.save(requestDto.getTitle(), requestDto.getContents(), loggedUser.getUsername(), loggedUser.getId());

        return new ResponseEntity<>(scheduleResponseDto, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Page<ScheduleResponseDto>> findAll(@PageableDefault(size = 10, sort = "modifiedAt", direction = Sort.Direction.DESC) Pageable pageable) {

        Page<ScheduleResponseDto> scheduleResponseDtoList = scheduleService.findAll(pageable);

        return new ResponseEntity<>(scheduleResponseDtoList, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Page<ScheduleResponseDto>> findAllByUserId(@PathVariable Long id, @PageableDefault(size = 10, sort = "modifiedAt", direction = Sort.Direction.DESC) Pageable pageable) {

        Page<ScheduleResponseDto> scheduleResponseDtoList = scheduleService.findByMemberId(id,pageable);

        return new ResponseEntity<>(scheduleResponseDtoList, HttpStatus.OK);
    }

    @GetMapping("/{userId}/{scheduleId}")
    public ResponseEntity<ScheduleResponseDto> findOneByUserId(@PathVariable Long userId, @PathVariable Long scheduleId) {

        ScheduleResponseDto scheduleResponseDto = scheduleService.findByMemberIdAndScheduleId(userId, scheduleId);

        return new ResponseEntity<>(scheduleResponseDto, HttpStatus.OK);
    }

    @PatchMapping("/{userId}/{scheduleId}")
    public ResponseEntity<Void> updateSchedule(@PathVariable Long userId, @PathVariable Long scheduleId, @Valid  @RequestBody UpdateScheduleRequestDto requestDto) {

        scheduleService.updateSchedule(userId, scheduleId, requestDto.getNewTitle(), requestDto.getNewContents());

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{userId}/{scheduleId}")
    public ResponseEntity<Void> deleteOneByUserId(@PathVariable Long userId, @PathVariable Long scheduleId) {

        scheduleService.deleteByUserIdAndScheduleId(userId, scheduleId);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
