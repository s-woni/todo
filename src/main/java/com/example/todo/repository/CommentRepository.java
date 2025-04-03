package com.example.todo.repository;

import com.example.todo.entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    // 일정 id에 해당하는 모든 댓글 조회
    Page<Comment> findByScheduleId(Long scheduleId, Pageable pageable);

    // 특정 일정에 있는 특정 댓글 조회
    Optional<Comment> findByScheduleIdAndId(Long scheduleId, Long commentId);
}
