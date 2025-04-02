package com.example.todo.service;

import com.example.todo.config.PasswordEncoder;
import com.example.todo.dto.LoginResponseDto;
import com.example.todo.dto.MemberResponseDto;
import com.example.todo.dto.SignUpResponseDto;
import com.example.todo.entity.Member;
import com.example.todo.repository.MemberRepository;
import com.example.todo.repository.ScheduleRepository;
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
    private final ScheduleRepository scheduleRepository;
    private final PasswordEncoder passwordEncoder;

    public SignUpResponseDto signUp(String username, String password, String email) {

        String encodePassword = passwordEncoder.encode(password);

        Member member = new Member(username, encodePassword, email);

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

        checkedPassword(findMember, oldPassword);

        String encodePassword = passwordEncoder.encode(newPassword);

        findMember.updatePassword(encodePassword);
    }

    public void delete(Long id, String password) {

        Member findMember = memberRepository.findByIdOrElseThrow(id);

        checkedPassword(findMember, password);

        memberRepository.deleteById(id);
    }

    @Transactional
    public void updateUserName(Long id, String newName, String password) {

        Member findMember = memberRepository.findByIdOrElseThrow(id);

        checkedPassword(findMember, password);

        findMember.updateUserName(newName);
    }

    public LoginResponseDto login(String email, String password) {

        Member findMember = memberRepository.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "존재하지 않는 아이디" + email));

        checkedPassword(findMember, password);

        return new LoginResponseDto(findMember.getId(), findMember.getUsername(), findMember.getEmail());
    }

    public void checkedPassword(Member member, String password) {

        String memberPassword = member.getPassword();

        if (!passwordEncoder.matches(password, memberPassword)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "잘못된 비밀번호");
        }
    }
}
