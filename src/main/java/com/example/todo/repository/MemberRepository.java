package com.example.todo.repository;

import com.example.todo.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    // id를 통해 회원 조회 시 없으면 예외 발생
    default Member findByIdOrElseThrow(Long id) {
        return findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "존재하지 않는 id : " + id));
    }

    // 이메일로 유저 조회
    Optional<Member> findByEmail(String email);
}
