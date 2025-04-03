package com.example.todo.repository;

import com.example.todo.entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    Page<Comment> findByScheduleId(Long scheduleId, Pageable pageable);

    Optional<Comment> findByScheduleIdAndId(Long scheduleId, Long commentId);
}
