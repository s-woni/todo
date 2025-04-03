package com.example.todo.repository;

import com.example.todo.entity.Schedule;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    // id값에 해당하는 일정 조회 시 없으면 예외 발생
    default Schedule findByIdOrElseThrow(Long id) {
        return findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Does not exist id = " + id));
    }

    // 특정 회원이 작성한 일정 목록 조회
    Page<Schedule> findByMemberId(Long memberId, Pageable pageable);

    // 특정 회원이 작성한 특정 일정 조회
    Optional<Schedule> findByMemberIdAndId(Long memberId, Long scheduleId);
}