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

    // 회원가입
    public SignUpResponseDto signUp(String username, String password, String email) {

        // 비밀번호 암호화
        String encodePassword = passwordEncoder.encode(password);

        // 유저 정보 저장
        Member member = new Member(username, encodePassword, email);
        Member savedMember = memberRepository.save(member);

        return new SignUpResponseDto(savedMember.getId(), savedMember.getUsername(), savedMember.getEmail());
    }
    
    // 로그인
    public LoginResponseDto login(String email, String password) {
        
        // 이메일로 유저 조회
        Member findMember = memberRepository.findByEmail(email)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "존재하지 않는 아이디" + email));
    
        // 비밀번호 검증
        checkedPassword(findMember, password);
        
        return new LoginResponseDto(findMember.getId(), findMember.getUsername(), findMember.getEmail());
    }

    // id로 유저 조회
    public MemberResponseDto findById(Long id) {

        Optional<Member> optionalMember = memberRepository.findById(id);

        if (optionalMember.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "존재하지 않는 id : " + id);
        }

        Member findMember = optionalMember.get();

        return new MemberResponseDto(findMember.getUsername(), findMember.getEmail());
    }

    // 비밀번호 변경
    @Transactional
    public void updatePassword(Long id, String oldPassword, String newPassword) {

        Member findMember = memberRepository.findByIdOrElseThrow(id);

        // 기존 비밀번호 검증
        checkedPassword(findMember, oldPassword);

        // 새 비밀번호 암호화
        String encodePassword = passwordEncoder.encode(newPassword);

        findMember.updatePassword(encodePassword);
    }

    // 유저 삭제
    public void delete(Long id, String password) {

        Member findMember = memberRepository.findByIdOrElseThrow(id);

        // 비밀번호 검증
        checkedPassword(findMember, password);

        memberRepository.deleteById(id);
    }

    // 이름 수정
    @Transactional
    public void updateUserName(Long id, String newName, String password) {

        Member findMember = memberRepository.findByIdOrElseThrow(id);

        // 비밀번호 검증
        checkedPassword(findMember, password);

        findMember.updateUserName(newName);
    }
    
    // 비밀번호 검증
    public void checkedPassword(Member member, String password) {

        String memberPassword = member.getPassword();

        if (!passwordEncoder.matches(password, memberPassword)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "잘못된 비밀번호");
        }
    }
}
