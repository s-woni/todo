package com.example.todo.controller;

import com.example.todo.common.Const;
import com.example.todo.dto.CommentRequestDto;
import com.example.todo.dto.CommentResponseDto;
import com.example.todo.dto.LoginResponseDto;
import com.example.todo.service.CommentService;
import com.example.todo.service.MemberService;
import com.example.todo.service.ScheduleService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("{scheduleId}/comments")
@RequiredArgsConstructor
public class CommentController {

    private final MemberService memberService;
    private final ScheduleService scheduleService;
    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<CommentResponseDto> save(@PathVariable Long scheduleId, @Valid @RequestBody CommentRequestDto requestDto, HttpServletRequest servletRequest) {

        LoginResponseDto loggedUser = (LoginResponseDto) servletRequest.getSession(false).getAttribute(Const.LOGIN_USER);

        CommentResponseDto commentResponseDto = commentService.save(requestDto.getComment(), requestDto.getUsername(), loggedUser.getId(), scheduleId);

        return new ResponseEntity<>(commentResponseDto, HttpStatus.CREATED);
    }
}
