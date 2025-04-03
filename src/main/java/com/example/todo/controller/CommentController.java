package com.example.todo.controller;

import com.example.todo.common.Const;
import com.example.todo.dto.CommentRequestDto;
import com.example.todo.dto.CommentResponseDto;
import com.example.todo.dto.LoginResponseDto;
import com.example.todo.dto.UpdateCommentRequestDto;
import com.example.todo.service.CommentService;
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
@RequestMapping("{scheduleId}/comments")
@RequiredArgsConstructor
public class CommentController {

    private final MemberService memberService;
    private final ScheduleService scheduleService;
    private final CommentService commentService;

    // 새로운 댓글을 저장
    @PostMapping
    public ResponseEntity<CommentResponseDto> save(@PathVariable Long scheduleId, @Valid @RequestBody CommentRequestDto requestDto, HttpServletRequest servletRequest) {

        // 세션에서 로그인 된 사용자 정보 가져오기
        LoginResponseDto loggedUser = (LoginResponseDto) servletRequest.getSession(false).getAttribute(Const.LOGIN_USER);

        // 댓글 저장
        CommentResponseDto commentResponseDto = commentService.save(requestDto.getComment(), loggedUser.getUsername(), loggedUser.getId(), scheduleId);

        return new ResponseEntity<>(commentResponseDto, HttpStatus.CREATED);
    }

    // 댓글 전체 조회
    @GetMapping
    public ResponseEntity<Page<CommentResponseDto>> findAll(@PathVariable Long scheduleId, @PageableDefault(size = 10, sort = "modifiedAt", direction = Sort.Direction.DESC) Pageable pageable) {

        Page<CommentResponseDto> commentResponseDtoList = commentService.findByScheduleId(scheduleId, pageable);

        return new ResponseEntity<>(commentResponseDtoList, HttpStatus.OK);
    }

    // 댓글 수정
    @PatchMapping("/{commentId}")
    public ResponseEntity<Void> updateComment(@PathVariable Long scheduleId, @PathVariable Long commentId, @Valid @RequestBody UpdateCommentRequestDto requestDto, HttpServletRequest servletRequest) {

        // 세션에서 로그인 된 사용자 정보 가져오기
        LoginResponseDto loggedUser = (LoginResponseDto) servletRequest.getSession(false).getAttribute(Const.LOGIN_USER);

        // 댓글 수정
        commentService.updateComment(loggedUser.getId(), scheduleId, commentId, requestDto.getNewComment());

        return new ResponseEntity<>(HttpStatus.OK);
    }

    // 댓글 삭제
    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long scheduleId, @PathVariable Long commentId, HttpServletRequest servletRequest) {

        // 세션에서 로그인 된 사용자 정보 가져오기
        LoginResponseDto loggedUser = (LoginResponseDto) servletRequest.getSession(false).getAttribute(Const.LOGIN_USER);

        // 댓글 삭제
        commentService.deleteComment(loggedUser.getId(), scheduleId, commentId);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
