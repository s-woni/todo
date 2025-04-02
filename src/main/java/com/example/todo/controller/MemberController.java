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

    @PostMapping("/signup")
    public ResponseEntity<SignUpResponseDto> signUp(@RequestBody SignUpRequestDto requestDto) {

        SignUpResponseDto signUpResponseDto = memberService.signUp(requestDto.getUsername(), requestDto.getPassword(), requestDto.getEmail());

        return new ResponseEntity<>(signUpResponseDto, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody @Valid LoginRequestDto requestDto,
                                                  HttpServletRequest servletRequest) {

        LoginResponseDto responseDto = memberService.login(requestDto.getEmail(), requestDto.getPassword());

        Long userId = responseDto.getId();

        if (userId == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        HttpSession session = servletRequest.getSession();
        session.setAttribute(Const.LOGIN_USER, responseDto);

        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpServletRequest servletRequest) {
        HttpSession session = servletRequest.getSession(false);

        if (session != null) {
            session.invalidate();
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MemberResponseDto> findById(@PathVariable Long id) {

        MemberResponseDto memberResponseDto = memberService.findById(id);

        return new ResponseEntity<>(memberResponseDto, HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> updatePasword(@PathVariable Long id, @RequestBody UpdatePasswordRequest requestDto) {

        memberService.updatePassword(id, requestDto.getOldPassword(), requestDto.getNewPassword());

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping("/{id}/name")
    public ResponseEntity<Void> updateUserName(@PathVariable Long id, @RequestBody UpdateUserNameRequest requestDto) {

        memberService.updateUserName(id, requestDto.getNewName(), requestDto.getPassword());

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMember(@PathVariable Long id) {

        memberService.delete(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
