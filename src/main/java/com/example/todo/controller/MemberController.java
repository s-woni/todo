package com.example.todo.controller;

import com.example.todo.common.Const;
import com.example.todo.dto.*;
import com.example.todo.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    // 회원가입
    @PostMapping("/signup")
    public ResponseEntity<SignUpResponseDto> signUp(@Valid @RequestBody SignUpRequestDto requestDto) {

        SignUpResponseDto signUpResponseDto = memberService.signUp(requestDto.getUsername(), requestDto.getPassword(), requestDto.getEmail());

        return new ResponseEntity<>(signUpResponseDto, HttpStatus.CREATED);
    }

    // 로그인
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@Valid @RequestBody LoginRequestDto requestDto,
                                                  HttpServletRequest servletRequest) {

        LoginResponseDto responseDto = memberService.login(requestDto.getEmail(), requestDto.getPassword());

        Long userId = responseDto.getId();

        if (userId == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        // 세션 생성 & 사용자 정보 저장
        HttpSession session = servletRequest.getSession();
        session.setAttribute(Const.LOGIN_USER, responseDto);
        session.setAttribute("sessionKey", responseDto.getId());

        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    // 로그아웃
    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpServletRequest servletRequest) {
        HttpSession session = servletRequest.getSession(false);

        if (session != null) {
            session.invalidate();
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

    // 유저 정보 조회
    @GetMapping("/{id}")
    public ResponseEntity<MemberResponseDto> findById(@PathVariable Long id, HttpServletRequest servletRequest) {

        HttpSession session = servletRequest.getSession(false);
        if (session == null || session.getAttribute(Const.LOGIN_USER) == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        LoginResponseDto loginResponseDto = (LoginResponseDto) session.getAttribute(Const.LOGIN_USER);
        Long userId = loginResponseDto.getId();

        if (!userId.equals(id)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        MemberResponseDto memberResponseDto = memberService.findById(id);

        return new ResponseEntity<>(memberResponseDto, HttpStatus.OK);
    }

    // 비밀번호 변경
    @PatchMapping("/{id}")
    public ResponseEntity<Void> updatePasword(@PathVariable Long id, @Valid @RequestBody UpdatePasswordRequestDto requestDto) {

        memberService.updatePassword(id, requestDto.getOldPassword(), requestDto.getNewPassword());

        return new ResponseEntity<>(HttpStatus.OK);
    }

    // 유저 이름 변경
    @PatchMapping("/{id}/name")
    public ResponseEntity<Void> updateUserName(@PathVariable Long id, @Valid @RequestBody UpdateUserNameRequestDto requestDto) {

        memberService.updateUserName(id, requestDto.getNewName(), requestDto.getPassword());

        return new ResponseEntity<>(HttpStatus.OK);
    }

    // 유저 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMember(@PathVariable Long id, @Valid @RequestBody DeleteMemberRequestDto requestDto) {

        memberService.delete(id, requestDto.getPassword());

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
