package com.example.todo.service;

import com.example.todo.dto.LoginResponseDto;
import com.example.todo.dto.MemberResponseDto;
import com.example.todo.dto.SignUpResponseDto;
import com.example.todo.entity.Member;
import com.example.todo.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public SignUpResponseDto signUp(String username, String password, String email) {

        Member member = new Member(username, password, email);

        Member savedMember = memberRepository.save(member);

        return new SignUpResponseDto(savedMember.getId(), savedMember.getUsername(), savedMember.getEmail());
    }

    public MemberResponseDto findById(Long id) {

        Optional<Member> optionalMember = memberRepository.findById(id);

        if (optionalMember.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "존재하지 않는 id : " + id);
        }

        Member findMember = optionalMember.get();

        return new MemberResponseDto(findMember.getUsername(), findMember.getEmail());
    }

    @Transactional
    public void updatePassword(Long id, String oldPassword, String newPassword) {

        Member findMember = memberRepository.findByIdOrElseThrow(id);

        if (!findMember.getPassword().equals(oldPassword)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "잘못된 비밀번호 : " + id);
        }

        findMember.updatePassword(newPassword);
    }

    public void delete(Long id) {

        Member findMember = memberRepository.findByIdOrElseThrow(id);

        memberRepository.delete(findMember);
    }

    @Transactional
    public void updateUserName(Long id, String newName, String password) {

        Member findMember = memberRepository.findByIdOrElseThrow(id);

        if (!findMember.getPassword().equals(password)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "잘못된 비밀번호 : " + id);
        }

        findMember.updateUserName(newName);
    }

    public LoginResponseDto login(String email, String password) {

        Member findMember = memberRepository.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "존재하지 않는 아이디" + email));

        if (!findMember.getPassword().equals(password)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "잘못된 비밀번호 : " + email);
        }

        return new LoginResponseDto(findMember.getId(), findMember.getUsername(), findMember.getEmail());
    }
}
